package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository rep;

    public List<Produto> getProdutos() {
        return rep.findAll();
    }

    public Produto getProdutoById(Long id) {
        Optional<Produto> optional = rep.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public Produto getProdutoeByIdSituacaoTrue(Long id){
        Optional<Produto> optional = rep.finByIdSituacaoTrue(id);
        if(optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public List<Produto> getProdutosByNome(String nome) {
        return new ArrayList<>(rep.findByNome(nome + "%"));
    }

    public List<Produto> getProdudosBySituacao(Boolean situacao){
        return new ArrayList<>(rep.findBySituacao(situacao));
    }

    public Produto insert(Produto produto) {
        Assert.isNull(produto.getId(),"Não foi possível inserir o registro");
        return rep.save(produto);
    }

    public Produto update(Produto produto) {
        Assert.notNull(produto.getId(),"Não foi possível atualizar o registro");

        // Busca o produto no banco de dados
        Optional<Produto> optional = rep.findById(produto.getId());
        if(optional.isPresent()) {
            Produto db = optional.get();
            // Copiar as propriedades
            db.setNome(produto.getNome());
            db.setDescricao(produto.getDescricao());
            db.setValor(produto.getValor());
            db.setEstoque(produto.getEstoque());
            db.setSituacao(produto.getSituacao());
            // Atualiza o produto
            rep.save(db);

            return db;
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(Long id) {
        rep.deleteById(id);
    }
}
