package io.vgrente.movies_api.domain

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "movie")
class Movie(

    @NotNull
    @JsonProperty("title")
    @Column(name = "title")
    var title: String,

    @NotNull
    @JsonProperty("release_date")
    @Column(name = "release_date")
    var releaseDate: LocalDate,

    @NotNull
    @JsonProperty("director_id")
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
}
