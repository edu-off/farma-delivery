package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.FarmaciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmaciaRepository extends JpaRepository<FarmaciaEntity, Long> {
}
