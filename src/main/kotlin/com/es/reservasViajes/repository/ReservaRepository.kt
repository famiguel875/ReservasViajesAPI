package com.es.reservasViajes.repository

import com.es.reservasViajes.model.Reserva
import org.springframework.data.jpa.repository.JpaRepository

interface ReservaRepository : JpaRepository<Reserva, Long>