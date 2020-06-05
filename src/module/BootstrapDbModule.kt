package module

import data.db.FilmTable
import data.db.OrderTable
import data.db.UserTable
import io.ktor.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class BootstrapDbModule(private val dataSource: DataSource): AppModule{
    override fun install(application: Application) {
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(FilmTable)
            SchemaUtils.create(OrderTable)
        }
    }

}