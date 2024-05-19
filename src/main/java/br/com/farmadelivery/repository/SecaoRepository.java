package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.SecaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecaoRepository extends JpaRepository<SecaoEntity, Long> {
}
