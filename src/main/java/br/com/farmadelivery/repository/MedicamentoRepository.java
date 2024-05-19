package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.MedicamentoEntity;
import br.com.farmadelivery.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<MedicamentoEntity, Long> {

    MedicamentoEntity findByProduto(ProdutoEntity produtoEntity);

}
