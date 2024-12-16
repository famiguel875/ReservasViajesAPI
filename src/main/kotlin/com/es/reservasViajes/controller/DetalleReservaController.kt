package com.es.reservasViajes.controller

import com.es.reservasViajes.model.DetalleReserva
import com.es.reservasViajes.service.DetalleReservaService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/detalles")
class DetalleReservaController(private val detalleService: DetalleReservaService) {

    @GetMapping
    fun findAll(): ResponseEntity<List<DetalleReserva>> = ResponseEntity.ok(detalleService.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<DetalleReserva> {
        val detalle = detalleService.findById(id)
        return if (detalle != null) {
            ResponseEntity.ok(detalle)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) // 404: Detalle no encontrado
        }
    }

    @PostMapping
    fun create(@RequestBody detalle: DetalleReserva): ResponseEntity<DetalleReserva> {
        return try {
            if (detalle.fechaInicio.isAfter(detalle.fechaFin)) {
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null) // 422: Inconsistencias en fechas
            } else {
                ResponseEntity.ok(detalleService.save(detalle))
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null) // 400: Datos inválidos
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody detalle: DetalleReserva): ResponseEntity<DetalleReserva> {
        return try {
            val existing = detalleService.findById(id) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
            if (detalle.fechaInicio.isAfter(detalle.fechaFin)) {
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null) // 422: Inconsistencias en fechas
            } else {
                val updated = existing.copy(
                    destino = detalle.destino,
                    fechaInicio = detalle.fechaInicio,
                    fechaFin = detalle.fechaFin,
                    precioTotal = detalle.precioTotal
                )
                ResponseEntity.ok(detalleService.save(updated))
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            detalleService.delete(id)
            ResponseEntity.ok("Detalle eliminado")
        } catch (e: EmptyResultDataAccessException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado") // 404
        }
    }
}