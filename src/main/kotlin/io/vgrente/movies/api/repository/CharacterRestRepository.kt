package io.vgrente.movies.api.repository

import io.vgrente.movies.api.domain.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "characters", collectionResourceRel = "characters", itemResourceRel = "character")
interface CharacterRestRepository : JpaRepository<Character, Long>
