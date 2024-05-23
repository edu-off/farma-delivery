package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

    @Query(value = "select produto from ProdutoEntity produto where produto.farmacia.documento = :farmaciaDocumento")
    List<ProdutoEntity> findByFarmaciaDocumento(@Param("farmaciaDocumento") Long farmaciaDocumento);

}
