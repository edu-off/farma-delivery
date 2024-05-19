package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.MeioPagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeioPagamentoRepository extends JpaRepository<MeioPagamentoEntity, Long> {
}
