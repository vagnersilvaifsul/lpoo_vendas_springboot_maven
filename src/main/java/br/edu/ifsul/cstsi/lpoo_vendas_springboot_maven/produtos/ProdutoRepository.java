package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {

    @Query(value = "SELECT p FROM Produto p where p.nome like ?1")
    List<Produto> findByNome(String nome);

    List<Produto> findBySituacao(Boolean situacao);

    @Query(value = "SELECT p FROM Produto p where p.id = ?1 and p.situacao=true")
    Optional<Produto> finByIdSituacaoTrue(Long id);
}
