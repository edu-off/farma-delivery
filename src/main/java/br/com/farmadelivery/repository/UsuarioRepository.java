package br.com.farmadelivery.repository;

import br.com.farmadelivery.entity.UsuarioEntity;
import br.com.farmadelivery.entity.UsuarioEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UsuarioEntityPk> {

    UsuarioEntity findByEmail(String email);

}
