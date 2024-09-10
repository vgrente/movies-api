package io.vgrente.movies.api.repository

import io.vgrente.movies.api.domain.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "movies", collectionResourceRel = "movies", itemResourceRel = "movie")
interface MovieRestRepository : JpaRepository<Movie, Long>
