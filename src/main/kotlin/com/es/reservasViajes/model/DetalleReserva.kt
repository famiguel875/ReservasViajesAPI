package com.es.reservasViajes.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "detalles_reserva")
data class DetalleReserva(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    val reserva: Reserva,

    var destino: String,

    var fechaInicio: LocalDate,

    var fechaFin: LocalDate,

    var precioTotal: BigDecimal
)