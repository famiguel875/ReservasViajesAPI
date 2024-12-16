package com.es.reservasViajes.controller

import com.es.reservasViajes.model.Reserva
import com.es.reservasViajes.service.ReservaService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reservas")
class ReservaController(private val reservaService: ReservaService) {

    @GetMapping
    fun findAll(): ResponseEntity<List<Reserva>> = ResponseEntity.ok(reservaService.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Reserva> {
        val reserva = reservaService.findById(id)
        return if (reserva != null) {
            ResponseEntity.ok(reserva)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) // 404: Reserva no encontrada
        }
    }

    @PostMapping
    fun create(@RequestBody reserva: Reserva): ResponseEntity<Reserva> {
        return try {
            ResponseEntity.ok(reservaService.save(reserva))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) // 400: Datos inválidos
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody reserva: Reserva): ResponseEntity<Reserva> {
        return try {
            ResponseEntity.ok(reservaService.update(id, reserva))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) // 404: Reserva no encontrada
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            reservaService.delete(id)
            ResponseEntity.ok("Reserva eliminada")
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se puede eliminar una reserva confirmada") // 403
        } catch (e: EmptyResultDataAccessException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada") // 404
        }
    }
}