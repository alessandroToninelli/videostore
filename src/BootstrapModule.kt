import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.inject
import presentation.routingModule
import javax.sql.DataSource

fun Application.bootstrapModule() {

    bootstrapDb()

    routingModule()

}

private fun Application.bootstrapDb() {
    val dataSource: DataSource by inject()
    Database.connect(dataSource)

}
