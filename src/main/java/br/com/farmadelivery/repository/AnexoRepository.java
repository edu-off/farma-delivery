package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.AnexoEntity;
import br.com.farmadelivery.entity.CartaoCreditoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnexoRepository extends JpaRepository<AnexoEntity, Long> {
}
