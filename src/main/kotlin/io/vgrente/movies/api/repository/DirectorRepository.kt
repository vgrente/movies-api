package io.vgrente.movies.api.repository

import io.vgrente.movies.api.domain.Director
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "directors", collectionResourceRel = "directors", itemResourceRel = "director")
interface DirectorRestRepository : JpaRepository<Director, Long>
