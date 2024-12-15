package com.es.reservasViajes.service

import com.es.reservasViajes.model.DetalleReserva
import com.es.reservasViajes.model.EstadoReserva
import com.es.reservasViajes.repository.DetalleReservaRepository
import com.es.reservasViajes.repository.ReservaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.temporal.ChronoUnit


@Service
class DetalleReservaService(
    private val detalleRepository: DetalleReservaRepository,
    private val reservaRepository: ReservaRepository
) {

    fun findAll(): List<DetalleReserva> = detalleRepository.findAll()

    fun findById(id: Long): DetalleReserva? = detalleRepository.findById(id).orElse(null)

    fun save(detalle: DetalleReserva): DetalleReserva {
        val reserva = reservaRepository.findById(detalle.reserva.id!!)
            .orElseThrow { Exception("Reserva no encontrada") }

        if (reserva.estado != EstadoReserva.CONFIRMADA) {
            throw Exception("Solo se pueden agregar detalles a reservas confirmadas")
        }

        validateDates(detalle)
        calculateTotalPrice(detalle)

        return detalleRepository.save(detalle)
    }

    fun update(id: Long, detalle: DetalleReserva): DetalleReserva {
        val existingDetalle = detalleRepository.findById(id).orElseThrow { Exception("Detalle no encontrado") }
        existingDetalle.destino = detalle.destino
        existingDetalle.fechaInicio = detalle.fechaInicio
        existingDetalle.fechaFin = detalle.fechaFin
        calculateTotalPrice(existingDetalle)
        return detalleRepository.save(existingDetalle)
    }

    fun delete(id: Long) {
        val detalle = detalleRepository.findById(id).orElseThrow { Exception("Detalle no encontrado") }
        detalleRepository.delete(detalle)
    }

    private fun validateDates(detalle: DetalleReserva) {
        if (detalle.fechaFin.isBefore(detalle.fechaInicio)) {
            throw Exception("La fecha de fin no puede ser anterior a la fecha de inicio")
        }
    }

    private fun calculateTotalPrice(detalle: DetalleReserva) {
        val days = ChronoUnit.DAYS.between(detalle.fechaInicio, detalle.fechaFin)
        val dailyRate = BigDecimal("100.00") // Tarifa fija por día
        detalle.precioTotal = dailyRate.multiply(BigDecimal(days))
    }
}