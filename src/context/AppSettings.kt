package context

import com.typesafe.config.Config
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.util.*
import javax.sql.DataSource

object AppSettings {

    val dataSource: DataSource by lazy {
        val config = HikariConfig("/hikari.properties")
        config.maxLifetime = 30000
        HikariDataSource(config)
    }

}

fun Config.toProperties() = Properties().also {
    for (e in this.entrySet()) {
        it.setProperty(e.key, this.getString(e.key))
    }
}