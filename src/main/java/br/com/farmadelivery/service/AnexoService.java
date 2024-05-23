package br.com.farmadelivery.service;

import br.com.farmadelivery.entity.AnexoEntity;
import br.com.farmadelivery.entity.MedicamentoEntity;
import br.com.farmadelivery.enums.TiposAnexoEnum;
import br.com.farmadelivery.exception.negocio.EntidadeNaoEncontradaException;
import br.com.farmadelivery.factory.FactoryAnexoEntity;
import br.com.farmadelivery.repository.AnexoRepository;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.util.Optional;

@Service
public class AnexoService {

    @Autowired
    private AnexoRepository anexoRepository;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private FactoryAnexoEntity factoryAnexoEntity;

    public Optional<AnexoEntity> consulta(Long id) {
        return anexoRepository.findById(id);
    }

    public byte[] consultaArquivoAnexo(Long id) {
        Optional<AnexoEntity> optional = anexoRepository.findById(id);
        if (optional.isEmpty())
            throw new EntidadeNaoEncontradaException("anexo não encontrado");
        return transformBlobToByteArray(optional.get().getAnexo());
    }

    @Transactional
    public void adiciona(Long medicamentoId, MultipartFile anexo, TiposAnexoEnum tipo) {
        Optional<MedicamentoEntity> optionalMedicamento = medicamentoService.consulta(medicamentoId);
        if (optionalMedicamento.isEmpty())
            throw new EntidadeNaoEncontradaException("medicamento não encontrado");

        Blob anexoBlob = transformMultipartFileToBlob(anexo);
        AnexoEntity anexoEntity = factoryAnexoEntity.build(optionalMedicamento.get(), tipo, anexoBlob);
        anexoRepository.save(anexoEntity);
    }

    private Blob transformMultipartFileToBlob(MultipartFile multipartFile) {
        Blob blob = null;
        try {
            blob = BlobProxy.generateProxy(multipartFile.getInputStream(), multipartFile.getSize());
        } catch (IOException e) {
            throw new IllegalArgumentException("anexo inválido");
        }
        return blob;
    }

    private byte[] transformBlobToByteArray(Blob blob) {
        try {
            return blob.getBinaryStream().readAllBytes();
        } catch (Exception e) {
            throw new IllegalArgumentException("anexo inválido");
        }
    }

}
