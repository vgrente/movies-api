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
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Size
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import java.io.Serializable
import java.time.LocalDate
import java.util.Objects.isNull

@Entity
@Table(name = "actor")
@Cache(region = "actorCache", usage = CacheConcurrencyStrategy.READ_WRITE)
data class Actor(
    @field:NotNull
    @field:Size(min = 2, max = 50)
    @JsonProperty("first_name")
    @Column(name = "first_name")
    var firstName: String,
    @field:NotNull
    @field:Size(min = 2, max = 50)
    @JsonProperty("last_name")
    @Column(name = "last_name")
    var lastName: String,
    @field:NotNull
    @field:PastOrPresent
    @JsonProperty("birth_date")
    @Column(name = "birth_date")
    var birthDate: LocalDate,
    @field:PastOrPresent
    @JsonProperty("death_date")
    @Column(name = "death_date")
    var deathDate: LocalDate?,
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToMany
    @JoinTable(
        name = "actor_character",
        joinColumns = [JoinColumn(name = "actor_id")],
        inverseJoinColumns = [JoinColumn(name = "character_id")],
    )
    var characters: MutableSet<Character>? = mutableSetOf()

    @JsonProperty("is_alive")
    private fun isAlive(): Boolean = isNull(deathDate)

    override fun toString(): String {
        return "Actor(id=$id, firstName='$firstName', lastName='$lastName')"
    }
}
