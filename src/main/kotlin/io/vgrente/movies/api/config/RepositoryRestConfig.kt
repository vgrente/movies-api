package io.vgrente.movies.api.config

import io.vgrente.movies.api.domain.Actor
import io.vgrente.movies.api.domain.Director
import io.vgrente.movies.api.domain.Movie
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry

@Configuration
class RepositoryRestConfig {
    @Bean
    fun repositoryRestConfigurer(): RepositoryRestConfigurer {
        return object : RepositoryRestConfigurer {
            override fun configureRepositoryRestConfiguration(
                config: RepositoryRestConfiguration?,
                cors: CorsRegistry?,
            ) {
                config!!.exposeIdsFor(
                    Actor::class.java,
                    Character::class.java,
                    Director::class.java,
                    Movie::class.java,
                )
            }
        }
    }
}
