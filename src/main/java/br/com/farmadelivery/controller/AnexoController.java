package br.com.farmadelivery.controller;

import br.com.farmadelivery.enums.TiposAnexoEnum;
import br.com.farmadelivery.service.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/anexo")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    @PostMapping(value = "/cadastra-receita-medica/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<String> cadastraReceitaMedica(@PathVariable Long id, @RequestPart MultipartFile receitaMedica) {
        anexoService.cadastra(id, receitaMedica, TiposAnexoEnum.RECEITA_MEDICA);
        return ResponseEntity.ok("receita médica cadastrada com sucesso");
    }

    @PostMapping(value = "/cadastra-documento-com-foto/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<String> cadastraDocumento(@PathVariable Long id, @RequestPart MultipartFile documentoComFoto) {
        anexoService.cadastra(id, documentoComFoto, TiposAnexoEnum.DOCUMENTO_COM_FOTO);
        return ResponseEntity.ok("documento com foto cadastrado com sucesso");
    }

    @GetMapping(value = "/consulta-receita-medica/{anexoId}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    private @ResponseBody byte[] consultaReceitaMedica(@PathVariable Long anexoId) {
        return anexoService.consultaArquivoAnexo(anexoId);
    }

    @GetMapping(value = "/consulta-documento-com-foto/{anexoId}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    private @ResponseBody byte[] consultaDocumentoComFoto(@PathVariable Long anexoId) {
        return anexoService.consultaArquivoAnexo(anexoId);
    }

}
