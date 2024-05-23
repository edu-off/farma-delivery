package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.MeioPagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeioPagamentoRepository extends JpaRepository<MeioPagamentoEntity, Long> {

    @Query(value = "select meio from MeioPagamentoEntity meio where meio.cliente.id = :clienteId")
    List<MeioPagamentoEntity> findByClienteId(@Param("clienteId") Long clienteId);

}
