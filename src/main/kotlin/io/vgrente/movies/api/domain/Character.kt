package io.vgrente.movies.api.domain

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
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
    var name: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToMany
    @JoinTable(
        name = "actor_character",
        joinColumns = [JoinColumn(name = "character_id")],
        inverseJoinColumns = [JoinColumn(name = "actor_id")],
    )
    var actors: MutableSet<Actor>? = mutableSetOf()

    @ManyToMany
    @JoinTable(
        name = "movie_character",
        joinColumns = [JoinColumn(name = "character_id")],
        inverseJoinColumns = [JoinColumn(name = "movie_id")],
    )
    var movies: MutableSet<Movie>? = mutableSetOf()
}
