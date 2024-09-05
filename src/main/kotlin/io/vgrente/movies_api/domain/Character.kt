package io.vgrente.movies_api.domain

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

@Entity
@Table(name = "character")
@Cache(region = "characterCache", usage = CacheConcurrencyStrategy.READ_WRITE)
class Character(

    @NotNull
    @JsonProperty("name")
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
}
