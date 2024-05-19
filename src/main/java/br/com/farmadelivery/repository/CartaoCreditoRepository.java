package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.CartaoCreditoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCreditoEntity, Long> {
}
