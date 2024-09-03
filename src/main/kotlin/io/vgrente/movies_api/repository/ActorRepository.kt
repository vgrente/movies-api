package io.vgrente.movies_api.repository

import io.vgrente.movies_api.domain.Actor
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource



@RepositoryRestResource(path = "actors", collectionResourceRel = "actors", itemResourceRel = "actor")
interface ActorRestRepository : JpaRepository<Actor, Long> {

    @Cacheable(value = ["actors"])
    override fun findAll(): MutableList<Actor>

}


