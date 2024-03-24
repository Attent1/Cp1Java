package br.com.fiap.Checkpoint1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import br.com.fiap.Checkpoint1.validation.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity

public class Conta {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)    
    private Long id;
    
    @Column(name = "numeroConta", unique=true)
    private Long numeroConta;    
        
    @Size(min=4)
    private String agencia;
    @NotBlank
    private String nome;
     
    @CPF(message = "CPF inválido")
    @Column(name = "cpf", unique=true)
    private String cpf; 

    @PastOrPresent
    private LocalDate dtAbertura;
    @Min(0)
    private BigDecimal saldoInicial;
    private Boolean ativa;    
    @NotBlank(message = "{conta.tipo.notblank}")
    @Tipo     
    private String tipo;  
    
}
