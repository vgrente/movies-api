package io.vgrente.movies.api.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import io.vgrente.AbstractIT
import io.vgrente.cleandb.CleanDatabase
import io.vgrente.movies.api.domain.Actor
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.MediaTypes.HAL_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

private const val BASE_PATH = "/api/actors"

@CleanDatabase
@SpringBootTest
class ActorRestRepositoryTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val actorRestRepository: ActorRestRepository,
) :
    AbstractIT() {
    val logger: Logger = LoggerFactory.getLogger(ActorRestRepositoryTests::class.java)

    @BeforeEach
    fun setup(
        @Autowired entityManager: EntityManager,
    ) {
        logger.info("========================================== CLEAN ACTOR CACHE ==========================================")
        entityManager.entityManagerFactory.cache.evictAll()
    }

    @Test
    fun `Should Create an Actor`(
        @Autowired objectMapper: ObjectMapper,
    ) {
        val actor = Actor("Daniel", "Craig", LocalDate.of(1968, 3, 2), null)

        val result =
            mockMvc.perform(
                post(BASE_PATH)
                    .accept(HAL_JSON)
                    .contentType(HAL_JSON)
                    .content(objectMapper.writeValueAsString(actor)),
            )
                .andExpect(status().isCreated)
                .andExpect(content().contentType(HAL_JSON))
                .andExpectAll(
                    jsonPath("$.id").value(notNullValue()),
                    jsonPath("$.first_name").value(actor.firstName),
                    jsonPath("$.last_name").value(actor.lastName),
                    jsonPath("$.birth_date").value(actor.birthDate.toString()),
                    jsonPath("$.death_date").value(nullValue()),
                    jsonPath("$.is_alive").value(true),
                ).andReturn()

        val actorId = JsonPath.read<Int>(result.response.contentAsString, "$.id")

        assertThat(actorRestRepository.getReferenceById(actorId.toLong()))
            .isNotNull
            .extracting(Actor::firstName, Actor::lastName, Actor::birthDate, Actor::deathDate)
            .containsExactly(actor.firstName, actor.lastName, actor.birthDate, null)
    }

    @Test
    fun `Should Get an Actor by ID`() {
        val actor =
            actorRestRepository.save(
                Actor(
                    "Sean",
                    "Connery",
                    LocalDate.of(1930, 8, 25),
                    LocalDate.of(2020, 10, 31),
                ),
            )

        mockMvc.perform(
            get("$BASE_PATH/${actor.id}")
                .accept(HAL_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(HAL_JSON))
            .andExpectAll(
                jsonPath("$.id").value(actor.id!!.toInt()),
                jsonPath("$.first_name").value(actor.firstName),
                jsonPath("$.last_name").value(actor.lastName),
                jsonPath("$.birth_date").value(actor.birthDate.toString()),
                jsonPath("$.death_date").value(actor.deathDate?.toString()),
                jsonPath("$.is_alive").value(false),
                jsonPath("$._links").isNotEmpty,
                jsonPath("$._links.self.href", containsString("$BASE_PATH/${actor.id}")),
                jsonPath("$._links.actor.href", containsString("$BASE_PATH/${actor.id}")),
                jsonPath("$._links.characters.href", containsString("$BASE_PATH/${actor.id}/characters")),
            )
    }

    @Test
    fun `Should Patch an Actor by ID`() {
        val actor =
            actorRestRepository.save(Actor("Pierse", "Bronan", LocalDate.of(1953, 5, 18), LocalDate.now()))

        mockMvc.perform(
            patch("$BASE_PATH/${actor.id}")
                .accept(HAL_JSON)
                .content(
                    """
                    {"first_name": "Pierce", "last_name": "Brosnan", "birth_date": "1953-05-16", "death_date": null}
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(HAL_JSON))
            .andExpectAll(
                jsonPath("$.id").value(actor.id!!.toInt()),
                jsonPath("$.first_name").value("Pierce"),
                jsonPath("$.last_name").value("Brosnan"),
                jsonPath("$.birth_date").value("1953-05-16"),
                jsonPath("$.death_date").value(nullValue()),
                jsonPath("$.is_alive").value(true),
            )
    }

    @Test
    fun `Should Delete an Actor by ID`() {
        val actor = actorRestRepository.save(Actor("Judi", "Dench", LocalDate.of(1934, 12, 9), null))

        mockMvc.perform(delete("$BASE_PATH/${actor.id}"))
            .andExpect(status().isNoContent)

        assertThat(actorRestRepository.findById(actor.id!!)).isEmpty
    }
}
