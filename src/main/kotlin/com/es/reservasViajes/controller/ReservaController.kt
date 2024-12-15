package com.es.reservasViajes.controller

import com.es.reservasViajes.model.Reserva
import com.es.reservasViajes.service.ReservaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/reservas")
class ReservaController(private val reservaService: ReservaService) {

    @GetMapping
    fun findAll(): ResponseEntity<List<Reserva>> {
        return ResponseEntity.ok(reservaService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Reserva> {
        val reserva = reservaService.findById(id) ?: throw Exception("Reserva no encontrada")
        return ResponseEntity.ok(reserva)
    }

    @PostMapping
    fun create(@RequestBody reserva: Reserva): ResponseEntity<Reserva> {
        val savedReserva = reservaService.save(reserva)
        return ResponseEntity.ok(savedReserva)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody reserva: Reserva): ResponseEntity<Reserva> {
        val updatedReserva = reservaService.update(id, reserva)
        return ResponseEntity.ok(updatedReserva)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        reservaService.delete(id)
        return ResponseEntity.ok("Reserva eliminada")
    }
}