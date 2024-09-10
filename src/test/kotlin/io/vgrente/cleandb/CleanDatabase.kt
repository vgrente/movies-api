package io.vgrente.cleandb

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.sql.DataSource

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(CleanDatabaseCallback::class)
annotation class CleanDatabase

private class CleanDatabaseCallback : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        val logger = LoggerFactory.getLogger(CleanDatabase::class.java)
        val dataSource = SpringExtension.getApplicationContext(context).getBean(DataSource::class.java)
        logger.info("========================================== CLEAN DATABASE ==========================================")
        ResourceDatabasePopulator(ClassPathResource("db/scripts/clean-db.sql")).execute(dataSource)
    }
}
