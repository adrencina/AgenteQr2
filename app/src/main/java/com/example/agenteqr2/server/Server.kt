package com.example.agenteqr2.server

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.CallLogging
import org.slf4j.event.Level

// Función para intercambiar el authorization code por el token
suspend fun exchangeAuthCodeForToken(authCode: String): String? {
    // Aquí deberías realizar la lógica para hacer la solicitud POST a Tiendanube
    // con el authCode y obtener el token de acceso.

    // A modo de ejemplo, asumimos que la respuesta es exitosa y el token recibido es "dummy_token"
    return "dummy_token"  // Reemplazar por la lógica real para obtener el token
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(CallLogging) {
            level = Level.INFO
        }

        routing {
            // Ruta de callback donde se recibe el authorization code
            get("/callback") {
                val authCode = call.request.queryParameters["code"]
                if (!authCode.isNullOrEmpty()) {
                    // Llamamos a la función para intercambiar el authorization code por el token
                    val tokenResponse = exchangeAuthCodeForToken(authCode)
                    if (tokenResponse != null) {
                        call.respond(HttpStatusCode.OK, "Access token received: $tokenResponse")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Failed to get access token")
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Missing authorization code")
                }
            }
        }
    }.start(wait = true)
}