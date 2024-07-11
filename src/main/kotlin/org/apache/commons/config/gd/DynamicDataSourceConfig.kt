/*
package org.apache.commons.config.gd

import com.appleyk.core.model.LicenseCustomManager
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties::class)
open class DynamicDataSourceConfig(val dynamicDataSourceProperties: DynamicDataSourceProperties) {

    @Bean
    open fun jdbcTemplate(dynamicDataSource: DataSource) = JdbcTemplate(dynamicDataSource)

    @Bean
    open fun dynamicDataSource(): DynamicDataSource {
        val dynamicDataSource = DynamicDataSource()
        val original: MutableMap<Any, Any> = HashMap()
        for ((key, value) in dynamicDataSourceProperties.datasource) {
            val hikariDataSource: HikariDataSource = getHikariDataSource(value)
            original[key] = hikariDataSource
        }
        dynamicDataSource.setTargetDataSources(original)
        return dynamicDataSource
    }
    private fun getHikariDataSource(value: DynamicDataSourceProperties.DSConfig): HikariDataSource {
        val hikariCp = value.hikari
        val hikari = getHikari(hikariCp)
        hikari.username = value.username
        hikari.password = value.password
        hikari.jdbcUrl = value.url
        hikari.driverClassName = value.driverClassName
        val hikariDataSource = HikariDataSource(hikari)
        return hikariDataSource
    }

    private fun getHikari(hikari: DynamicDataSourceProperties.DSConfig.HKConfig): HikariConfig {
        val hikariConfig = HikariConfig()
        hikariConfig.maximumPoolSize = hikari.maximumPoolSize
        hikariConfig.minimumIdle = hikari.minimumIdle
        hikariConfig.idleTimeout = hikari.idleTimeout
        hikariConfig.connectionTimeout = hikari.connectionTimeout
        hikariConfig.connectionTestQuery = hikari.connectionTestQuery
        return hikariConfig
    }
}

class DynamicDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): String {
        return DataSourceKeyHolder.getKey()
    }
}

object DataSourceKeyHolder {
    private val keyHolder = ThreadLocal<String>()
    const val MAIN_DB: String = "gdmdata"
    const val SOUTH_DB: String = "gdldata-sourth"

    const val NORTH_DB: String = "gdldata-north"

    fun getKey():String{
        val key = keyHolder.get()
        if (key.isNullOrEmpty()) {
            return MAIN_DB
        }
        return key
    }
    fun setKey(key:String){
        keyHolder.remove()
        keyHolder.set(key)
    }

    fun clear() {
        keyHolder.remove()
    }
}

@ConstructorBinding
@ConfigurationProperties(prefix = "dynamic")
data class DynamicDataSourceProperties(
    val datasource: Map<String, DSConfig>
) {
    data class DSConfig(
        val username: String,
        val password: String,
        val url: String,
        val driverClassName: String,
        val hikari: HKConfig
    ) {
        data class HKConfig(
            val maximumPoolSize: Int,
            val minimumIdle: Int,
            val idleTimeout: Long,
            val connectionTimeout: Long,
            val connectionTestQuery: String
        )
    }
}

fun <R> LicenseCustomManager.notBlank(block: LicenseCustomManager.() -> R): R? {
    if (this.verify().info.isNullOrBlank()) {
        return block()
    }
    return null
}


*/
