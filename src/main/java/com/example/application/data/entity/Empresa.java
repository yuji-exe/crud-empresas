package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Empresa extends AbstractEntity{

    @Column(name = "nome_fantasia", length = 80)
    @NotEmpty
    private String nomeFantasia;

    @Column(name = "razao_social", length = 120)
    @NotEmpty
    private String razaoSocial;

    @Column(length = 18)
    @NotEmpty
    private String cnpj;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_fundacao")
    private Date dataFundacao;

    @ManyToOne
    @JoinColumn(name = "ramo_atividade_id")
    @NotNull
    private RamoAtividade ramoAtividade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @NotNull
    private TipoEmpresa tipo;

    @Column(precision = 10, scale = 2)
    private BigDecimal faturamento;

}
