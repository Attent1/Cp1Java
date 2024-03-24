package br.com.fiap.Checkpoint1.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND; 
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.Checkpoint1.model.Conta;
import br.com.fiap.Checkpoint1.repository.ContaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("conta")

public class ContaController {
    
    @Autowired
    ContaRepository repository;
    
    @GetMapping 
    public List<Conta> listar() {
        return repository.findAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void encerrarConta(@PathVariable Long id){
        Conta conta = verificarSeExisteConta(id);                  
        conta.setAtiva(false);
        repository.save(conta);
    }

    @PutMapping("/deposito/{id}/{valor}")
    @ResponseStatus(OK)
    public Conta deposito(@PathVariable Long id, @PathVariable BigDecimal valor) throws Exception{

        Conta conta = verificarSeExisteConta(id);
        if (!Ativa(conta)) {
            throw new Exception("Conta inativa não pode fazer depósito");
        }
        if (valor.compareTo(new BigDecimal(0)) > 0){
            conta.setSaldoInicial(conta.getSaldoInicial().add(valor));
        }                 

        return repository.save(conta);
    }

    @PutMapping("/pix/{idOrig}/{idDest}/{valor}")
    @ResponseStatus(OK)
    public List<Conta> PIX(@PathVariable Long idOrig, @PathVariable Long idDest,  @PathVariable BigDecimal valor) throws Exception{
        Conta contaOrig = verificarSeExisteConta(idOrig);                                    
        Conta contaDest = verificarSeExisteConta(idDest);

        saque(idOrig, valor);           
        deposito(idDest, valor);

        List<Conta> lista = new ArrayList<>(); 
    
        lista.add(contaOrig);
        lista.add(contaDest);
            
        return lista;   
    }

    @PutMapping("/saque/{id}/{valor}")
    @ResponseStatus(OK)
    public Conta saque(@PathVariable Long id, @PathVariable BigDecimal valor) throws Exception{

        Conta conta = verificarSeExisteConta(id);
        if (!Ativa(conta)) {
            throw new Exception("Conta inativa não pode fazer saque");
        }
        if (conta.getSaldoInicial().compareTo(valor) > 0){
            conta.setSaldoInicial(conta.getSaldoInicial().subtract(valor));
        }                  
        
        return repository.save(conta);
    }
    
    private boolean Ativa(Conta conta){
        return conta.getAtiva();
    }

    private Conta verificarSeExisteConta(Long id) {
        return repository.findById(id)
                  .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Conta não encontrada"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Conta create (@RequestBody @Valid Conta conta){       
        return repository.save(conta);
    }

    @GetMapping("{id}")
    public ResponseEntity<Conta> get(@PathVariable Long id){                
        return repository
                    .findById(id)
                    .map(ResponseEntity::ok) 
                    .orElse(ResponseEntity.notFound().build());                        
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<Conta> buscar(@PathVariable String cpf) {

        return repository.findContaByCpf(cpf)
                  .map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());                        
    }
    
}
