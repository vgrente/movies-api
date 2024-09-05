package io.vgrente.movies_api.domain

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

@Entity
@Table(name = "director")
@Cache(region = "directorCache", usage = CacheConcurrencyStrategy.READ_WRITE)
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
