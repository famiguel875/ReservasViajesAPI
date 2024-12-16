package com.es.reservasViajes.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var nombre: String,

    @Column(unique = true, nullable = false)
    var correo: String,

    var contraseña: String,

    val fechaCreacion: LocalDateTime = LocalDateTime.now(),

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = [JoinColumn(name = "usuario_id")])
    @Column(name = "role")
    val roles: Set<String> = setOf("CLIENTE")
)