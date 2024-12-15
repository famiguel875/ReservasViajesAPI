package com.es.reservasViajes.repository

import com.es.reservasViajes.model.DetalleReserva
import org.springframework.data.jpa.repository.JpaRepository

interface DetalleReservaRepository : JpaRepository<DetalleReserva, Long>