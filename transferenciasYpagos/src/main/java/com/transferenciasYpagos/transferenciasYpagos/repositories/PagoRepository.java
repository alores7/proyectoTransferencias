package com.transferenciasYpagos.transferenciasYpagos.repositories;

import com.transferenciasYpagos.transferenciasYpagos.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
}
