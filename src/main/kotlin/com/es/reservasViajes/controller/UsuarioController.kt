package com.es.reservasViajes.controller

import com.es.reservasViajes.model.Usuario
import com.es.reservasViajes.service.TokenService
import com.es.reservasViajes.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService,
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val tokenService: TokenService
) {

    @PostMapping
    fun create(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        return try {
            val savedUser = usuarioService.save(usuario)
            ResponseEntity.ok(savedUser)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null) // 409: Conflicto (correo duplicado)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) // 400: Datos inválidos
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.findById(id)
        return if (usuario != null) {
            ResponseEntity.ok(usuario)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) // 404: Usuario no encontrado
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        return try {
            val updatedUser = usuarioService.update(id, usuario)
            ResponseEntity.ok(updatedUser)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) // 404: Usuario no encontrado
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            usuarioService.delete(id)
            ResponseEntity.ok("Usuario eliminado")
        } catch (e: EmptyResultDataAccessException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado") // 404
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.correo, usuario.contraseña))
        } catch (e: AuthenticationException) {
            return ResponseEntity(mapOf("mensaje" to "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }

        // Generar el token si la autenticación fue exitosa
        val token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }
}

