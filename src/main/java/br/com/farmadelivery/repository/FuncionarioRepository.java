package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.FuncionarioEntity;
import br.com.farmadelivery.entity.FuncionarioEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, FuncionarioEntityPk> {

    @Query(value = "select funcionario from FuncionarioEntity funcionario where funcionario.farmacia.documento = :farmaciaDocumento")
    List<FuncionarioEntity> findByFarmaciaDocumento(@Param("farmaciaDocumento") Long farmaciaDocumento);


}
