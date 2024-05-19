package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.FuncionarioEntity;
import br.com.farmadelivery.entity.FuncionarioEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, FuncionarioEntityPk> {
}
