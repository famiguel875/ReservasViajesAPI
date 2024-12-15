package com.es.reservasViajes.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservas")
data class Reserva(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario,

    val fechaReserva: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var estado: EstadoReserva = EstadoReserva.PENDIENTE
)

enum class EstadoReserva {
    PENDIENTE, CONFIRMADA, CANCELADA
}
