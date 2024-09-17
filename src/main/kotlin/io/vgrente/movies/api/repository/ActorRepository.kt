package io.vgrente.movies.api.repository

import io.vgrente.movies.api.domain.Actor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "actors", collectionResourceRel = "actors", itemResourceRel = "actor")
interface ActorRestRepository : CrudRepository<Actor, Long>
