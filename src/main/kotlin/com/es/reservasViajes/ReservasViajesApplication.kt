package com.es.reservasViajes

import com.es.reservasViajes.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class ReservasViajesApplication

fun main(args: Array<String>) {
    runApplication<ReservasViajesApplication>(*args)
}