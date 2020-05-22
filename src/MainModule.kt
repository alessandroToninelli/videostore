import di.ModuleManager
import io.ktor.application.*
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.*
import kotlinx.coroutines.time.withTimeout
import org.koin.ktor.ext.koin
import util.suspendCoroutineWithTimeout
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


    launch{
//        val a = suspendCoroutineWithTimeout<Int>(4000) {cont->
//            launch {
//                delay(20000)
//                cont.resume(3)
//            }.invokeOnCompletion { println("completato") }
//
//
////            cont.invokeOnCancellation { println("timeOut");cont.resumeWithException(it!!); job.cancel() }
//        }

//        val a = withTimeoutOrNull(2000){
//            delay(20000)
//            2
//        }
//
//
//
//        println("prima di a")
//        println(a)
//        println("dopo di a")
    }


    koin {
        ModuleManager.loadModules()
    }

    bootstrapModule()
}



suspend fun exec(){

}
