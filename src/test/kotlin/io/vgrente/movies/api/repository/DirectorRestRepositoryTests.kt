package io.vgrente.movies.api.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import io.vgrente.AbstractIT
import io.vgrente.cleandb.CleanDatabase
import io.vgrente.matchers.streamToIsMatcher
import io.vgrente.movies.api.domain.Director
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers.containsInAnyOrder
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
import java.util.stream.IntStream

private const val BASE_PATH = "/api/directors"

@CleanDatabase
@SpringBootTest
class DirectorRestRepositoryTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val directorRestRepository: DirectorRestRepository,
) : AbstractIT() {
    val logger: Logger = LoggerFactory.getLogger(CharacterRestRepositoryTests::class.java)

    @BeforeEach
    fun setup(
        @Autowired entityManager: EntityManager,
    ) {
        logger.info("========================================== CLEAN CACHE ==========================================")
        entityManager.entityManagerFactory.cache.evictAll()
    }

    @Test
    fun `Should find available Directors using pagination`() {
        directorRestRepository.deleteAll()
        val directors =
            IntStream.rangeClosed(1, 50)
                .mapToObj { index ->
                    directorRestRepository.save(
                        Director("FirstName $index", "LastName $index"),
                    )
                }
                .toList()

        mockMvc.perform(
            get(BASE_PATH)
                .accept(HAL_JSON)
                .param("page", "0")
                .param("size", directors.size.toString()),
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(HAL_JSON))
            .andExpectAll(
                jsonPath("$._embedded.directors.length()").value(directors.size),
                jsonPath(
                    "$._embedded.directors[*].id",
                    containsInAnyOrder(streamToIsMatcher(directors.stream().map { it.id!!.toInt() })),
                ),
                jsonPath(
                    "$._embedded.directors[*].first_name",
                    containsInAnyOrder(streamToIsMatcher(directors.stream().map(Director::firstName))),
                ),
                jsonPath(
                    "$._embedded.directors[*].last_name",
                    containsInAnyOrder(streamToIsMatcher(directors.stream().map(Director::lastName))),
                ),
                jsonPath("$.page").isNotEmpty,
                jsonPath("$.page.size").value(directors.size),
                jsonPath("$.page.total_elements").value(directors.size),
                jsonPath("$.page.total_pages").value(1),
                jsonPath("$.page.number").value(0),
            )
    }

    @Test
    fun `Should Get a Director by ID`() {
        val director = directorRestRepository.save(Director("Terence", "Young"))

        mockMvc.perform(
            get("$BASE_PATH/${director.id}")
                .accept(HAL_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(HAL_JSON))
            .andExpectAll(
                jsonPath("$.id").value(director.id!!.toInt()),
                jsonPath("$.first_name").value(director.firstName),
                jsonPath("$.last_name").value(director.lastName),
                jsonPath("$._links").isNotEmpty,
                jsonPath("$._links.self.href", containsString("$BASE_PATH/${director.id}")),
                jsonPath("$._links.director.href", containsString("$BASE_PATH/${director.id}")),
                jsonPath("$._links.movies.href", containsString("$BASE_PATH/${director.id}/movies")),
            )
    }

    @Test
    fun `Should Create a Director`(
        @Autowired objectMapper: ObjectMapper,
    ) {
        val director = Director("Terence", "Young")

        val result =
            mockMvc.perform(
                post(BASE_PATH)
                    .accept(HAL_JSON)
                    .content(objectMapper.writeValueAsString(director)),
            )
                .andExpect(status().isCreated)
                .andExpect(content().contentType(HAL_JSON))
                .andExpectAll(
                    jsonPath("$.id").value(notNullValue()),
                    jsonPath("$.first_name").value(director.firstName),
                    jsonPath("$.last_name").value(director.lastName),
                )
                .andReturn()

        val directorId = JsonPath.read<Int>(result.response.contentAsString, "$.id")

        assertThat(directorRestRepository.getReferenceById(directorId.toLong()))
            .isNotNull
            .extracting(Director::firstName, Director::lastName)
            .containsExactly(director.firstName, director.lastName)
    }

    @Test
    fun `Should Patch a Director by ID`() {
        val director = directorRestRepository.save(Director("Martine", "Campbelly"))

        mockMvc.perform(
            patch("$BASE_PATH/${director.id}")
                .accept(HAL_JSON)
                .content(
                    """
                    {"first_name": "Martin", "last_name": "Campbell"}
                    """.trimIndent(),
                ),
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(HAL_JSON))
            .andExpectAll(
                jsonPath("$.id").value(director.id!!.toInt()),
                jsonPath("$.first_name").value("Martin"),
                jsonPath("$.last_name").value("Campbell"),
            )
    }

    @Test
    fun `Should Delete a Director by ID`() {
        val director = directorRestRepository.save(Director("John", "Glen"))

        mockMvc.perform(delete("$BASE_PATH/${director.id}"))
            .andExpect(status().isNoContent)

        assertThat(directorRestRepository.findById(director.id!!)).isEmpty
    }
}
