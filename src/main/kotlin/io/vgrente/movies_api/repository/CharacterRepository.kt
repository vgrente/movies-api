package io.vgrente.movies_api.repository

import io.vgrente.movies_api.domain.Character
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(path = "characters", collectionResourceRel = "characters", itemResourceRel = "character")
interface CharacterRestRepository : JpaRepository<Character, Long>

