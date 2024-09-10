package io.vgrente.movies.api.repository

import io.vgrente.movies.api.domain.Actor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "actors", collectionResourceRel = "actors", itemResourceRel = "actor")
interface ActorRestRepository : JpaRepository<Actor, Long>
