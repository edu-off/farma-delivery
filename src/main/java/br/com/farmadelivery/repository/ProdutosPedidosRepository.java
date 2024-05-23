package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.ProdutosPedidosEntity;
import br.com.farmadelivery.entity.ProdutosPedidosEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutosPedidosRepository extends JpaRepository<ProdutosPedidosEntity, ProdutosPedidosEntityPk> {

    List<ProdutosPedidosEntity> findAllByPedidoId(Long pedidoId);
}
