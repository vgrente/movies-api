package io.vgrente.movies_api.repository

import io.vgrente.movies_api.domain.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "movies", collectionResourceRel = "movies", itemResourceRel = "movie")
interface MovieRestRepository : JpaRepository<Movie, Long>
