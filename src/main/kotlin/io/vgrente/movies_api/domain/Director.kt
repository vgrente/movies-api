package io.vgrente.movies_api.domain

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "director")
class Director(

    @NotNull
    @JsonProperty("first_name")
    @Column(name = "first_name")
    var firstName: String,

    @NotNull
    @JsonProperty("last_name")
    @Column(name = "last_name")
    var lastName: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "director")
    var movies: MutableSet<Movie>? = mutableSetOf()
}
