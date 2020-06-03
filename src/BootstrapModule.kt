import io.ktor.application.*
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authentication
import io.ktor.auth.basic
import io.ktor.auth.form
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.inject
import presentation.routingModule
import javax.sql.DataSource

fun Application.bootstrapModule() {

    bootstrapDb()

    bootstrapLoginProvider()

    routingModule()

}

private fun Application.bootstrapDb() {
    val dataSource: DataSource by inject()
    Database.connect(dataSource)
}


private fun Application.bootstrapLoginProvider(){
     authentication {
         form {
             userParamName = "name"
             passwordParamName = "psw"
             validate {
                 if(it.name == it.password)
                     UserIdPrincipal("utente")
                 else
                     null
             }
         }
     }
}
