package io.vgrente.movies_api.domain

import io.vgrente.movies_api.dto.CharacterDTO
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "character")
class Character(

    @NotNull
    @Column(name = "name")
    var name: String?

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToMany
    @JoinTable(
        name = "actor_character",
        joinColumns = [JoinColumn(name = "character_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")]
    )
    var actors: MutableSet<Actor>? = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "movie_character",
        joinColumns = [JoinColumn(name = "character_id")],
        inverseJoinColumns = [JoinColumn(name = "movie_id")]
    )
    var movies: MutableSet<Movie>? = mutableSetOf()

    fun toDTO(): CharacterDTO {
        return CharacterDTO(this.id!!, this.name!!, this.movies!!.map { it.toDTO() }.toMutableSet())
    }
}
