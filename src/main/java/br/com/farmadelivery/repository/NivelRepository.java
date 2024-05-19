package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.NivelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NivelRepository extends JpaRepository<NivelEntity, Long> {
}
