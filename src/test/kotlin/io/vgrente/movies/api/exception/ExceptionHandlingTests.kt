package io.vgrente.movies.api.exception

import io.vgrente.AbstractIT
import io.vgrente.cleandb.CleanDatabase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@CleanDatabase
@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlingTests : AbstractIT() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Should return 404 when resource is not found`() {
        mockMvc.perform(get("/api/actors/999999"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.message").value("EntityRepresentationModel not found"))
            .andExpect(jsonPath("$.details").exists())
            .andExpect(jsonPath("$.timestamp").exists())
    }

    @Test
    fun `Should return 400 for invalid input`() {
        val invalidActorJson =
            """
            {
                "first_name": "",
                "last_name": "Doe",
                "birth_date": "invalid-date"
            }
            """.trimIndent()

        mockMvc.perform(
            post("/api/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidActorJson),
        )
            .andExpect(status().isBadRequest)
            .andExpect(
                jsonPath(
                    "$.message",
                ).value(
                    "JSON parse error: Cannot deserialize value of type `java.time.LocalDate` " +
                        "from String \"invalid-date\": Failed to deserialize java.time.LocalDate: " +
                        "(java.time.format.DateTimeParseException) Text 'invalid-date' could not be " +
                        "parsed at index 0",
                ),
            )
    }
}
