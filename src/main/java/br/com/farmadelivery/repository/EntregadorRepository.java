package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.EntregadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregadorRepository extends JpaRepository<EntregadorEntity, Long> {

    EntregadorEntity findFirstByEstaAlocadoIsFalse();
}
