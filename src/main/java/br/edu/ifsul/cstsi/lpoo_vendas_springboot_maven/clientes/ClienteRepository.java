package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    @Query(value = "SELECT c FROM Cliente c where c.nome like ?1")
    List<Cliente> findByNome(String nome);

    List<Cliente> findBySituacao(Boolean situacao);

    @Query(value = "SELECT c FROM Cliente c where c.id = ?1 and c.situacao=true")
    Optional<Cliente> finByIdSituacaoTrue(Long id);
}
