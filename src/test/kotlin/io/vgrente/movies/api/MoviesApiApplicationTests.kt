package io.vgrente.movies.api

import io.vgrente.AbstractIT
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MoviesApiApplicationTests :
    AbstractIT() {

    @Test
    fun contextLoads() {
    }

    @Test
    fun main() {
        MoviesApiApplication()
    }
}
