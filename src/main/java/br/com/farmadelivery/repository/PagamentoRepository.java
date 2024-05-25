package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<PagamentoEntity, Long> {

    @Query("select pagamento from PagamentoEntity pagamento inner join PedidoEntity pedido on pedido.id = pagamento.pedido.id where pedido.id = :pedidoId")
    PagamentoEntity findByPedidoId(@Param("pedidoId") Long pedidoId);
}
