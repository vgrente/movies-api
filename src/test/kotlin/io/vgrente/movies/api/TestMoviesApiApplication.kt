package io.vgrente.movies.api

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<MoviesApiApplication>().with(TestcontainersConfiguration::class).run(*args)
}
