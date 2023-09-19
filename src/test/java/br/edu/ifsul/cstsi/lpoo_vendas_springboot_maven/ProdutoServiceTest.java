package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven;

import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos.Produto;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService service;

    @Test
    public void getProdutosTest() {
        List<Produto> produtos = service.getProdutos();
        assertEquals(5, produtos.size());
    }

    @Test
    public void getProdutoByIdTest(){
        Produto p = service.getProdutoById(1L);
        assertNotNull(p);
        assertEquals("Café", p.getNome());
    }

    @Test
    public void getProdutoeByIdSituacaoTrueTest(){
        Produto p = service.getProdutoeByIdSituacaoTrue(1L);
        assertNotNull(p);
        assertEquals("Café", p.getNome());
    }

    @Test
    public void getProdutosByNomeTest(){
        assertEquals(1, service.getProdutosByNome("Café").size());
        assertEquals(1, service.getProdutosByNome("Arroz").size());
        assertEquals(1, service.getProdutosByNome("Feijão").size());
    }

    @Test
    public void getProdudosBySituacaoTest(){
        assertEquals(4, service.getProdudosBySituacao(true).size());
    }

    @Test
    public void insertTest() {

        //cria o produto para teste
        Produto produto = new Produto();
        produto.setNome("Teste");
        produto.setDescricao("Desc. do produto Teste");
        produto.setValor(10.00);
        produto.setEstoque(100);
        produto.setSituacao(true);

        //insere o produto na base da dados
        Produto p = service.insert(produto);

        //se inseriu
        assertNotNull(p);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = p.getId();
        assertNotNull(id);
        p = service.getProdutoById(id);
        assertNotNull(p);

        //compara os valores inseridos com os valores pesquisados para confirmar
        assertEquals("Teste", p.getNome());
        assertEquals("Desc. do produto Teste", p.getDescricao());
        assertEquals(10.00, p.getValor());
        assertEquals(Integer.valueOf(100), p.getEstoque());
        assertEquals(Boolean.TRUE, p.getSituacao());

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        assertNull(service.getProdutoById(id));
    }

    @Test
    public void updateTest(){
        Produto p = service.getProdutoById(1L);
        String nome = p.getNome(); //armazena o valor original para voltar na base
        p.setNome("Café modificado");

        p = service.update(p);
        assertNotNull(p);
        assertEquals("Café modificado", p.getNome());

        //volta ao valor original
        p.setNome(nome);
        p = service.update(p);
        assertNotNull(p);
    }

    @Test
    public void deleteTest(){
        //cria o produto para teste
        Produto produto = new Produto();
        produto.setNome("Teste");
        produto.setDescricao("Desc. do produto Teste");
        produto.setValor(1.00);
        produto.setEstoque(1);
        produto.setSituacao(true);

        //insere o produto na base da dados
        Produto p = service.insert(produto);

        //se inseriu
        assertNotNull(p);

        //confirma se o produto foi realmente inserido na base de dados
        Long id = p.getId();
        assertNotNull(id);
        p = service.getProdutoById(id);
        assertNotNull(p);

        // Deletar o objeto
        service.delete(id);
        //Verificar se deletou
        assertNull(service.getProdutoById(id));
    }
}
