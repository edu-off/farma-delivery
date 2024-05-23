package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.PedidoEntity;
import br.com.farmadelivery.enums.StatusPedidoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findAllByStatus(StatusPedidoEnum status);

}
