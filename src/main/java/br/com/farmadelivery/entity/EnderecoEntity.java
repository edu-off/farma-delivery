package br.com.farmadelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "endereco")
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endereco;

    private String bairro;

    private String cidade;

    private String uf;

    private Integer cep;

    @OneToOne(mappedBy = "endereco")
    private ClienteEntity cliente;

    @OneToOne(mappedBy = "endereco")
    private FarmaciaEntity farmacia;

}
