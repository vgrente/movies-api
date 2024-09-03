package io.vgrente.movies_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication

@EnableSpringDataWebSupport(
    pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO
)
class MoviesApiApplication

fun main(args: Array<String>) {
    runApplication<MoviesApiApplication>(*args)
}
