package com.es.reservasViajes.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(private val rsaKeys: RSAKeysProperties) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth -> auth
                // Rutas públicas
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll() // Crear usuario
                .requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll() // Login de usuario

                // Rutas autenticadas para usuarios generales
                .requestMatchers(HttpMethod.GET, "/usuarios/**").authenticated() // Obtener usuario por ID
                .requestMatchers(HttpMethod.PUT, "/usuarios/**").authenticated() // Actualizar usuario
                .requestMatchers(HttpMethod.DELETE, "/usuarios/**").authenticated() // Eliminar usuario

                .requestMatchers(HttpMethod.POST, "/reservas").authenticated() // Crear reserva
                .requestMatchers(HttpMethod.GET, "/reservas").authenticated() // Obtener todas las reservas
                .requestMatchers(HttpMethod.GET, "/reservas/**").authenticated() // Obtener reserva por ID
                .requestMatchers(HttpMethod.POST, "/detalles").authenticated() // Crear detalle reserva
                .requestMatchers(HttpMethod.GET, "/detalles").authenticated() // Obtener todos los detalles
                .requestMatchers(HttpMethod.GET, "/detalles/**").authenticated() // Obtener detalle por ID
                .requestMatchers(HttpMethod.PUT, "/detalles/**").authenticated() // Actualizar detalle

                // Rutas específicas para ADMIN
                .requestMatchers(HttpMethod.PUT, "/reservas/**").hasRole("ADMIN") // Actualizar reserva
                .requestMatchers(HttpMethod.DELETE, "/reservas/**").hasRole("ADMIN") // Eliminar reserva
                .requestMatchers(HttpMethod.DELETE, "/detalles/**").hasRole("ADMIN") // Eliminar detalle

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeys.publicKey).privateKey(rsaKeys.privateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey).build()
    }
}