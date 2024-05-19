package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.EntregaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaRepository extends JpaRepository<EntregaEntity, Long> {
}
