package com.es.reservasViajes.service

import com.es.reservasViajes.model.Usuario
import com.es.reservasViajes.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService @Autowired constructor(
    private val repository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

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

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByCorreo(username!!)
            ?: throw Exception("Usuario no encontrado") // Reemplazamos orElseThrow por elvis ?: con excepción

        return User.builder()
            .username(usuario.correo)
            .password(usuario.contraseña)
            .roles(*usuario.roles.toTypedArray())
            .build()
    }
}
