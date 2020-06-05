package module

import di.ModuleManager
import io.ktor.application.*
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.getKoin
import org.koin.ktor.ext.koin


fun main(args: Array<String>) {
    val server = embeddedServer(Netty, commandLineEnvironment(args))

    server.start()
}

fun Application.mainModule() {

    koin {
        ModuleManager.loadModules()

    }

    getKoin().getAll<AppModule>().onEach { it.install(this) }

}
