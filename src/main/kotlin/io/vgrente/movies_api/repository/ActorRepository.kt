package io.vgrente.movies_api.repository

import io.vgrente.movies_api.domain.Actor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource


@RepositoryRestResource(path = "actors", collectionResourceRel = "actors", itemResourceRel = "actor")
interface ActorRestRepository : CrudRepository<Actor, Long>
