package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.NivelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NivelRepository extends JpaRepository<NivelEntity, Long> {

    @Query(value = "select nivel from NivelEntity nivel where nivel.farmacia.documento = :farmaciaDocumento")
    List<NivelEntity> findByFarmaciaDocumento(@Param("farmaciaDocumento") Long farmaciaDocumento);

}
