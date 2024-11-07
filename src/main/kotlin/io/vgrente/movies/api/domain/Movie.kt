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
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.time.LocalDate

@Entity
@Table(name = "movie")
@Cache(region = "movieCache", usage = CacheConcurrencyStrategy.READ_WRITE)
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
    var director: Director,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToMany
    @JoinTable(
        name = "movie_character",
        joinColumns = [JoinColumn(name = "movie_id")],
        inverseJoinColumns = [JoinColumn(name = "character_id")],
    )
    var characters: MutableSet<Character>? = mutableSetOf()
}
