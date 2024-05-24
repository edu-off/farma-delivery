package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.MedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, Long> {

    @Query(value = "select medicamento from MedicamentoEntity medicamento where medicamento.produto.id = :produtoId")
    MedicamentoEntity findByProdutoId(@Param("produtoId") Long produtoId);

    @Query(value = "select medicamento from MedicamentoEntity medicamento where medicamento.produto.farmacia.documento = :farmaciaDocumento")
    List<MedicamentoEntity> findByFarmaciaDocumento(@Param("farmaciaDocumento") Long farmaciaDocumento);

}
