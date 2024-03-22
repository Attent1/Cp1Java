package br.com.fiap.Checkpoint1.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND; 
import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("{id}")
    @ResponseStatus(OK)
    public Conta encerrarConta(@PathVariable Long id, @RequestBody Conta conta){
        verificarSeExisteConta(id);                  
        conta.setId(id); 
        conta.setAtiva(false);
        return repository.save(conta);
    }

    @PutMapping("/deposito/{id}/{valor}")
    @ResponseStatus(OK)
    public Conta deposito(@PathVariable Long id, @PathVariable Double valor , @RequestBody Conta conta){
        Double valorAtual;
        verificarSeExisteConta(id); 
        if (valor > 0){
            valorAtual = conta.getSaldoInicial();
            valorAtual += valor;            
            conta.setSaldoInicial(valorAtual);
        }                 
        conta.setId(id); 
        conta.setAtiva(false);
        return repository.save(conta);
    }

    @PutMapping("/deposito/pix/{idOrig}/{idDest}/{valor}")
    @ResponseStatus(OK)
    public List<Conta> PIX(@PathVariable Long idOrig, @PathVariable Long idDest,  @PathVariable Double valor){
        Double valorAtual;
        verificarSeExisteConta(idOrig); 
        verificarSeExisteConta(idDest);

        var contaOrig = repository.getById(idOrig);            

        var contaDest = repository.getById(idDest);
                    
        if (valor > 0){
            valorAtual = contaOrig.getSaldoInicial();
            valorAtual -= valor;            
            contaOrig.setSaldoInicial(valorAtual);
        }                 
        contaOrig.setId(idOrig);    

        deposito(idDest, valor, contaDest);
        List<Conta> lista = new ArrayList<>(); 
    
        lista.add(contaOrig);
        lista.add(contaDest);
            
        return lista;   
    }

    @PutMapping("/saque/{id}/{valor}")
    @ResponseStatus(OK)
    public Conta saque(@PathVariable Long id, @PathVariable Double valor , @RequestBody Conta conta){
        Double valorAtual;
        verificarSeExisteConta(id); 
        if (valor > 0){
            valorAtual = conta.getSaldoInicial();
            valorAtual -= valor;            
            conta.setSaldoInicial(valorAtual);
        }                 
        conta.setId(id); 
        conta.setAtiva(false);
        return repository.save(conta);
    }
    
    private void verificarSeExisteConta(Long id) {
        repository.findById(id)
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
        Conta obj = findCPF(cpf);
        if (obj !=null) {
            
            return ResponseEntity.ok().body(obj);
        }
        return ResponseEntity.notFound().build();
    }

    public Conta findCPF(String cpf) {
        Optional<Conta> obj = repository.findByCpf(cpf); 
    return obj.orElseThrow();
}

}
