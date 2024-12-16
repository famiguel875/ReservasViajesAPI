package com.es.reservasViajes.service

import com.es.reservasViajes.model.Usuario
import com.es.reservasViajes.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun save(usuario: Usuario): Usuario {
        usuario.contraseña = passwordEncoder.encode(usuario.contraseña)
        return repository.save(usuario)
    }

    fun findById(id: Long): Usuario? = repository.findById(id).orElse(null)

    fun update(id: Long, usuario: Usuario): Usuario {
        val existingUser = repository.findById(id).orElseThrow { Exception("Usuario no encontrado") }
        existingUser.nombre = usuario.nombre
        existingUser.correo = usuario.correo
        existingUser.contraseña = passwordEncoder.encode(usuario.contraseña)
        return repository.save(existingUser)
    }

    fun delete(id: Long) {
        val usuario = repository.findById(id).orElseThrow { Exception("Usuario no encontrado") }
        repository.delete(usuario)
    }

    fun verificarCredenciales(correo: String, contraseña: String): Usuario? {
        val usuario = repository.findByCorreo(correo)
            ?: throw IllegalArgumentException("Usuario no encontrado con el correo proporcionado")

        // Verificar si la contraseña coincide
        if (!passwordEncoder.matches(contraseña, usuario.contraseña)) {
            throw IllegalArgumentException("Contraseña incorrecta")
        }

        return usuario
    }
}