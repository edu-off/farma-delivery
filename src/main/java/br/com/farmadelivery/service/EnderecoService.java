package br.com.farmadelivery.service;

import br.com.farmadelivery.domain.Endereco;
import br.com.farmadelivery.entity.EnderecoEntity;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryEnderecoEntity;
import br.com.farmadelivery.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private FactoryEnderecoEntity factoryEnderecoEntity;

    public Optional<EnderecoEntity> consulta(Long id) {
        return enderecoRepository.findById(id);
    }

    @Transactional
    public EnderecoEntity cadastra(Endereco endereco) {
        return enderecoRepository.save(factoryEnderecoEntity.buildFromEndereco(endereco));
    }

    @Transactional
    public void altera(Long id, Endereco endereco) {
        Optional<EnderecoEntity> optional = consulta(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("endereço não encontrado");

        EnderecoEntity entity = optional.get();
        entity.setEndereco(endereco.getEndereco());
        entity.setBairro(endereco.getBairro());
        entity.setCidade(endereco.getCidade());
        entity.setUf(endereco.getUf());
        entity.setCep(endereco.getCep());
        enderecoRepository.save(entity);
    }

}
