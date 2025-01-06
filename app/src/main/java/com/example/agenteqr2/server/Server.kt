package com.example.agenteqr2.server

import com.example.agenteqr2.server.AuthService.exchangeAuthCodeForToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import org.slf4j.event.Level

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
                    val tokenResponse = runBlocking { exchangeAuthCodeForToken(authCode) }
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