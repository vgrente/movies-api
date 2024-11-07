package io.vgrente

import org.slf4j.LoggerFactory
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIT {
    companion object {
        private val logger = LoggerFactory.getLogger(AbstractIT::class.java)

        @Container
        val redisContainer: GenericContainer<*> =
            GenericContainer(DockerImageName.parse("redis:latest"))
                .withExposedPorts(6379)
                .withLogConsumer(Slf4jLogConsumer(logger))

        @Container
        val postgreSQLContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer(DockerImageName.parse("postgres:15-alpine"))
                .withExposedPorts(5432)
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test")
                .withLogConsumer(Slf4jLogConsumer(logger))

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgreSQLContainer::getUsername)
            registry.add("spring.datasource.password", postgreSQLContainer::getPassword)
            logger.info("PostgresSQL container started at: ${postgreSQLContainer.jdbcUrl}")
            logger.info("Redis container started at: ${redisContainer.host}:${redisContainer.portBindings}")
        }

        init {
            val redisPortBindings: MutableList<String> = ArrayList()
            redisPortBindings.add("6379:6379")
            redisContainer.portBindings = redisPortBindings
            redisContainer.start()
            postgreSQLContainer.start()
        }
    }
}
