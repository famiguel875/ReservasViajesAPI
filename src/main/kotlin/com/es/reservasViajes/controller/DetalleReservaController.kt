package com.es.reservasViajes.controller

import com.es.reservasViajes.model.DetalleReserva
import com.es.reservasViajes.service.DetalleReservaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/detalles")
class DetalleReservaController(private val detalleService: DetalleReservaService) {

    @GetMapping
    fun findAll(): ResponseEntity<List<DetalleReserva>> {
        return ResponseEntity.ok(detalleService.findAll())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<DetalleReserva> {
        val detalle = detalleService.findById(id) ?: throw Exception("Detalle no encontrado")
        return ResponseEntity.ok(detalle)
    }

    @PostMapping
    fun create(@RequestBody detalle: DetalleReserva): ResponseEntity<DetalleReserva> {
        return ResponseEntity.ok(detalleService.save(detalle))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody detalle: DetalleReserva): ResponseEntity<DetalleReserva> {
        val existing = detalleService.findById(id) ?: throw Exception("Detalle no encontrado")
        val updated = existing.copy(
            destino = detalle.destino,
            fechaInicio = detalle.fechaInicio,
            fechaFin = detalle.fechaFin,
            precioTotal = detalle.precioTotal
        )
        return ResponseEntity.ok(detalleService.save(updated))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        detalleService.delete(id)
        return ResponseEntity.ok("Detalle eliminado")
    }
}