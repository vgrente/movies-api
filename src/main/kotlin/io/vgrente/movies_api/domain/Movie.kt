package io.vgrente.movies_api.domain

import io.vgrente.movies_api.dto.MovieDTO
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "movie")
class Movie(

    @NotNull
    @Column(name = "title")
    var title: String,

    @NotNull
    @Column(name = "release_date")
    var releaseDate: LocalDate,

    @NotNull
    @ManyToOne
    @JoinColumn(name = "director_id")
    var director: Director

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToMany
    @JoinTable(
        name = "movie_character",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "character_id")]
    )
    var characters: MutableSet<Character>? = mutableSetOf()

    fun toDTO(): MovieDTO {
        return MovieDTO(this.id!!, this.title, this.releaseDate, this.director.toDTO())
    }
}
