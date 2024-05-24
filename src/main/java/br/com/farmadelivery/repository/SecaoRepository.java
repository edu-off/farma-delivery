package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.SecaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecaoRepository extends JpaRepository<SecaoEntity, Long> {

    @Query(value = "select secao from SecaoEntity secao where secao.farmacia.documento = :farmaciaDocumento")
    List<SecaoEntity> findByFarmaciaDocumento(@Param("farmaciaDocumento") Long farmaciaDocumento);

}
