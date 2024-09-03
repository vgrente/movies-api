package io.vgrente.movies_api.domain

import io.vgrente.movies_api.dto.DirectorDTO
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "director")
class Director(

    @NotNull
    @Column(name = "first_name")
    var firstName: String,

    @NotNull
    @Column(name = "last_name")
    var lastName: String

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "director")
    var movies: MutableSet<Movie>? = mutableSetOf()

    fun toDTO() :DirectorDTO{
        return DirectorDTO(this.id!!,this.firstName,this.lastName)
    }
}
