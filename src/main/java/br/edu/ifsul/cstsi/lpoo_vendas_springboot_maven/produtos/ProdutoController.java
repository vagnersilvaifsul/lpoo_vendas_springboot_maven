package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class ProdutoController {

    private static final Scanner input = new Scanner(System.in);
    private static ProdutoService produtoService;

    //Injeção de dependência (Não utilizou @Autowired porque o Springboot não injeta a dependência. Afinal, estamos adaptando um framework web para modo texto)
    public ProdutoController(ProdutoService produtoService) {
        ProdutoController.produtoService = produtoService;
    }

    public static void main(String[] args) {

        int opcao;
        do {
            System.out.print("\n\"-------  MENU PRODUTO -------\"");
            System.out.print(
                """

                    1. Inserir novo produto
                    2. Atualizar um produto
                    3. Excluir um produto (tornar inativo)
                    4. Ativar ou Desativar um produto
                    5. Listar todos os produtos
                    6. Buscar produto pelo código
                    7. Buscar produtos pelo nome
                    8. Buscar produtos pela situação
                    Opção (Zero p/sair):\s""");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();
                case 3 -> excluir();
                case 4 -> ativar();
                case 5 -> selectProdutos();
                case 6 -> selectProdutosById();
                case 7 -> selectProdutosByNome();
                case 8 -> selectProdutosBySituacao();
                default -> {
                    if (opcao != 0) System.out.println("Opção inválida.");
                }
            }
        } while (opcao != 0);
    }

    //opção 1
    private static void inserir() {
        Produto produto = new Produto();
        System.out.println("\n++++++ Cadastro de novo Produto ++++++");
        System.out.print("Digite o nome do produto: ");
        produto.setNome(input.nextLine());
        System.out.print("\nDigite a descrição do produto: ");
        produto.setDescricao(input.nextLine());
        System.out.print("\nDigite o valor do produto: ");
        produto.setValor(input.nextDouble());
        input.nextLine(); //limpa o input
        System.out.print("\nDigite a quantidade em estoque: ");
        produto.setEstoque(input.nextInt());
        input.nextLine(); //limpa o input
        produto.setSituacao(true);
        System.out.println("Produto salvo com sucesso:" + produtoService.insert(produto));
    }

    //opção 2
    private static void atualizar() {
        System.out.println("\n++++++ Alterar um Produto ++++++");
        Produto produto;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do produto (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else {
                produto = produtoService.getProdutoById(codigo);
                if (produto == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println("Nome: " + produto.getNome());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.println("Digite o novo nome do produto: ");
                        produto.setNome(input.nextLine());
                    }
                    System.out.println("Descrição: " + produto.getDescricao());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Digite a nova descrição do produto: ");
                        produto.setDescricao(input.nextLine());
                    }
                    System.out.println("Valor: " + produto.getValor());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        System.out.print("Digite o novo valor do produto: ");
                        produto.setValor(input.nextDouble());
                        input.nextLine();
                    }
                    System.out.println("Estoque: " + produto.getEstoque());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Digite o novo estoque do produto: ");
                        produto.setEstoque(input.nextInt());
                        input.nextLine();
                    }
                    produto.setSituacao(true);
                    if(produtoService.update(produto) != null) {
                        System.out.println("produto atualizado com sucesso:");
                        System.out.println(produtoService.getProdutoById(codigo));
                    } else {
                        System.out.println("Não foi possível atualizar este produto. Por favor, contate o administrador.");
                    }

                    opcao = 1;
                }
            }
        } while (opcao != 0);
    }

    private static void excluir() {
        System.out.println("\n++++++ Excluir um Produto ++++++");
        Produto produto;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do produto (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if(codigo > 0){
                produto = produtoService.getProdutoById(codigo);
                if (produto == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(produto);
                    System.out.print("Excluir este produto? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (0-sim/1-não) ");
                        produto.setSituacao(false);
                        input.nextLine();
                        produtoService.delete(produto.getId());
                        System.out.println("Produto excluído com sucesso:" + produto);
                    }

                }
            } else {
                System.out.println("Digite um código válido!!!");
            }
        } while (opcao != 0);
    }

    private static void ativar(){
        System.out.println("\n++++++ Ativar um Produto ++++++");
        Produto produto;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do produto (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if(codigo > 0){
                produto = produtoService.getProdutoById(codigo);
                if (produto == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(produto);
                    System.out.print("Ativar/Desativar este produto? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (0-sim/1-não) ");
                        produto.setSituacao(!produto.getSituacao());
                        input.nextLine();
                        if(produtoService.update(produto) != null) {
                            System.out.println("Situação do produto alterada com sucesso: " + produtoService.getProdutoById(codigo));
                        } else {
                            System.out.println("Não foi possível ativar/desativar este produto. Por favor, contate o administrador.");
                        }
                    }

                }
            } else {
                System.out.println("Digite um código válido!!!");
            }
        } while (opcao != 0);
    }

    //opção 3
    private static void selectProdutos() {
        System.out.println("\nLista de produtos cadastrados no banco de dados:\n" + produtoService.getProdutos());
    }

    //opção 4
    private static void selectProdutosById() {
        System.out.print("\nDigite o código do produto: ");
        Produto produto = produtoService.getProdutoById(input.nextLong());
        input.nextLine();
        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Código não localizado.");
        }
    }

    //opção 5
    private static void selectProdutosByNome() {
        System.out.print("Digite o nome do produto: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Produto> produtos = produtoService.getProdutosByNome(nome + "%");
        if (produtos.isEmpty()) {
            System.out.println("Não há registros correspondentes para: " + nome);
        } else {
            System.out.println(produtos);
        }
    }

    //opção 6
    private static void selectProdutosBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Produto> produtos;
        switch (situacao) {
            case 0 -> {
                produtos = produtoService.getProdudosBySituacao(false);
                System.out.println("Produtos na situação INATIVO:\n " + produtos);
            }
            case 1 -> {
                produtos = produtoService.getProdudosBySituacao(true);
                System.out.println("Produtos na situação ATIVO:\n " + produtos);
            }
        }
    }

}//fim classe
