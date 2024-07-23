import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

object Server {
    private fun updateShipment(data: String) {
        data.replace(" ", "")
        val shipmentAction = data.split(",")
        ActionFactory(shipmentAction.toMutableList()).callAction()
    }

    fun startServer() {
        embeddedServer(Netty, 8080) {
            routing {
                get("/") {
                    call.respondText(File("./src/main/resources/index.html").readText(), ContentType.Text.Html)
                }

                post("/data") {
                    val data = call.receiveText()
                    println(data)
                    updateShipment(data)
                    call.respondText { "POST Data Successful" }
                }
            }
        }.start(wait = false)
    }
}