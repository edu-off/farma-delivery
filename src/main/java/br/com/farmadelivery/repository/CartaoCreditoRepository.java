package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.CartaoCreditoEntity;
import br.com.farmadelivery.entity.MedicamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCreditoEntity, Long> {

    @Query(value = "select cartaoCredito from CartaoCreditoEntity cartaoCredito where cartaoCredito.meioPagamento.id = :meioPagamentoId")
    CartaoCreditoEntity findByMeioPagamentoId(@Param("meioPagamentoId") Long meioPagamentoId);

}
