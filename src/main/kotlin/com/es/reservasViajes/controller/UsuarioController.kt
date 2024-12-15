package com.es.reservasViajes.controller

import com.es.reservasViajes.model.Usuario
import com.es.reservasViajes.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    @PostMapping
    fun create(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val savedUser = usuarioService.save(usuario)
        return ResponseEntity.ok(savedUser)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.findById(id) ?: throw Exception("Usuario no encontrado")
        return ResponseEntity.ok(usuario)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val updatedUser = usuarioService.update(id, usuario)
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        usuarioService.delete(id)
        return ResponseEntity.ok("Usuario eliminado")
    }
}
