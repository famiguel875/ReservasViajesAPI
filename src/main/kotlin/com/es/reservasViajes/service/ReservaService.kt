package com.es.reservasViajes.service

import com.es.reservasViajes.model.EstadoReserva
import com.es.reservasViajes.model.Reserva
import com.es.reservasViajes.repository.ReservaRepository
import org.springframework.stereotype.Service

@Service
class ReservaService(private val repository: ReservaRepository) {

    fun findAll(): List<Reserva> = repository.findAll()

    fun findById(id: Long): Reserva? = repository.findById(id).orElse(null)

    fun save(reserva: Reserva): Reserva = repository.save(reserva)

    fun update(id: Long, reserva: Reserva): Reserva {
        val existingReserva = repository.findById(id).orElseThrow { Exception("Reserva no encontrada") }
        existingReserva.estado = reserva.estado
        return repository.save(existingReserva)
    }

    fun delete(id: Long) {
        val reserva = repository.findById(id).orElseThrow { Exception("Reserva no encontrada") }
        if (reserva.estado == EstadoReserva.CONFIRMADA) {
            throw Exception("No se puede eliminar una reserva confirmada")
        }
        repository.delete(reserva)
    }
}
