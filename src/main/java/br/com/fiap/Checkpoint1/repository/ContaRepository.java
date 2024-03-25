package br.com.fiap.Checkpoint1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.Checkpoint1.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
    
        Optional<Conta> findContaByCpf(String cpf);
        Optional<Conta> findByNome(String nome);
}
