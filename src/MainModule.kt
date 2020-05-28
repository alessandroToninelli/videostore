import di.ModuleManager
import io.ktor.application.*
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.*
import kotlinx.coroutines.time.withTimeout
import org.koin.ktor.ext.koin
import util.suspendCoroutineWithTimeout
import vo.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


fun main(args: Array<String>) {
    val server = embeddedServer(Netty, commandLineEnvironment(args))

    server.start()
}

fun Application.mainModule() {

    koin {
        ModuleManager.loadModules()
    }

    bootstrapModule()
}