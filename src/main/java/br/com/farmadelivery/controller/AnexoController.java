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

    @PostMapping(value = "/adiciona-receita-medica/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<String> adicionaReceitaMedica(@PathVariable Long id, @RequestPart MultipartFile receitaMedica) {
        anexoService.adiciona(id, receitaMedica, TiposAnexoEnum.RECEITA_MEDICA);
        return ResponseEntity.ok("receita médica adicionada com sucesso");
    }

    @PostMapping(value = "/adiciona-documento-com-foto/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<String> adicionaDocumento(@PathVariable Long id, @RequestPart MultipartFile documentoComFoto) {
        anexoService.adiciona(id, documentoComFoto, TiposAnexoEnum.DOCUMENTO_COM_FOTO);
        return ResponseEntity.ok("documento com foto adicionado com sucesso");
    }

    @GetMapping(value = "/recupera-receita-medica/{anexoId}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    private @ResponseBody byte[] recuperaReceitaMedica(@PathVariable Long anexoId) {
        return anexoService.consultaArquivoAnexo(anexoId);
    }

    @GetMapping(value = "/recupera-documento-com-foto/{anexoId}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    private @ResponseBody byte[] recuperaDocumentoComFoto(@PathVariable Long anexoId) {
        return anexoService.consultaArquivoAnexo(anexoId);
    }

}
