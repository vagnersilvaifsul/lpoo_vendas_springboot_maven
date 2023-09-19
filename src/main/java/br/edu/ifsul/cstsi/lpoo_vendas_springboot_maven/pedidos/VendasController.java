package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.pedidos;

import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes.Cliente;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes.ClienteService;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.itens.Item;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.itens.ItemService;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos.Produto;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.produtos.ProdutoService;
import org.springframework.stereotype.Controller;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class VendasController {
	
	private static final Scanner input = new Scanner(System.in);
    private static ClienteService clienteService;
    private static ProdutoService produtoService;
    private static PedidoService pedidoService;

    private static ItemService itemService;

    //Injeção de dependência (Não utilizou @Autowired porque o Springboot não injeta a dependência. Afinal, estamos adaptando um framework web para modo texto)
    public VendasController(ClienteService clienteService, ProdutoService produtoService, PedidoService pedidoService, ItemService itemService) {
        VendasController.clienteService = clienteService;
        VendasController.produtoService = produtoService;
        VendasController.pedidoService = pedidoService;
        VendasController.itemService = itemService;
    }

    public static void main(String[] args) {
        int opcao;
        Cliente cliente;
        Produto produto;
        List<Item> itens = new ArrayList<>();
        do {
            opcao = 0;
            System.out.println("\n\n******** Vendas ********");
            System.out.print("Digite o código do cliente: ");
            long codigoCliente = input.nextLong();
            input.nextLine();
            cliente = clienteService.getClienteByIdSituacaoTrue(codigoCliente);
            if(cliente == null){
                System.out.println("Código inválido ou inativo");
                opcao = 1;
            }else{
                System.out.println("Cliente selecionado: " + cliente);
                int sair = 2;
                do{
                    System.out.print("Digite o código do produto: ");
                    long codigoProduto = input.nextLong();
                    input.nextLine();
                    produto = produtoService.getProdutoeByIdSituacaoTrue(codigoProduto);
                    if(produto == null){
                        System.out.println("Código inválido ou inativo");
                        sair = 1;
                    }else{
                        System.out.println("Produto selecionado:" + produto);
                        System.out.print("Digite a quantidade: ");
                        int quantidade = input.nextInt();
                        input.nextLine();
                        if(quantidade > produto.getEstoque()){
                            System.out.println("Quantidade inválida.");
                            sair = 1;
                        }else{
                            Item item = new Item(produto);
                            item.setQuantidade(quantidade);
                            item.setTotalItem(quantidade * produto.getValor());
                            item.setSituacao(true);
                            itens.add(item);
                            System.out.println("Produto adionado ao carrinho.");
                            baixarEstoque(item); //baixa o estoque ao adicionar no carrinho
                            System.out.print("\nDeseja vender outro produto (sim-1/não-2)? ");
                            sair = input.nextInt();
                            input.nextLine();
                        }
                    }
                }while(sair != 2);
                if (itens.isEmpty()) {
                    System.out.println("\n******* Seu carrinho está VAZIO*******");
                } else { //se tem itens no carrinho
                    System.out.println("\n******* Seu carrinho *******");
                    itens.forEach( i -> { //firula para alinhar as colunas na impressão do carrinho
                        StringBuilder nome = new StringBuilder(i.getProduto().getNome());
                        StringBuilder precoUnitario = new StringBuilder(NumberFormat.getCurrencyInstance().format(i.getProduto().getValor()));
                        int MAX = 20;
                        if(nome.length() <= MAX){
                            nome.append(" ".repeat(Math.max(0, MAX - nome.length())));
                        }
                        if(precoUnitario.length() <= MAX){
                            precoUnitario.append(" ".repeat(Math.max(0, MAX - 5 - precoUnitario.length())));
                        } //fim da firula
                        System.out.println(
                                "\tProduto: " + nome +
                                "\tValor unidade = " +  precoUnitario +
                                "\t\tQuantidade = " + i.getQuantidade() +
                                "\t\tTotalItem = " + (NumberFormat.getCurrencyInstance().format(i.getQuantidade()*i.getProduto().getValor()))
                                );
                    });
                    double totalPedido = itens.stream().mapToDouble(Item::getTotalItem).sum();
                    System.out.println("*************************************\n" + "TOTAL DO PEDIDO = " + NumberFormat.getCurrencyInstance().format(totalPedido));
                    System.out.print("Fechar o pedido?(1-sim/2-não) ");
                    opcao = input.nextInt();
                    input.nextLine();
                    if(opcao == 1){
                        //salva o pedido
                        Pedido pedido = new Pedido(null, "visa débito", Estado.ABERTO, LocalDate.now(), LocalDate.now(), totalPedido, true, cliente, null);
                        Pedido pedidoSalvo = pedidoService.insert(pedido);
                        if(pedidoSalvo != null){
                            itens.forEach(i -> {
                                i.setPedido(pedidoSalvo);
                                itemService.insert(i);
                            });
                        }
                        System.out.println("Pedido salvo. " + pedidoSalvo + " \nItens adquiridos:" + itens);
                    }else if(opcao == 2){
                        System.out.print("Ops! Tem certeza? Você perderá esse pedido. (sim-1/não-2) ");
                        opcao = input.nextInt();
                        input.nextLine();
                        if(opcao == 1){
                            System.out.println("Pedido cancelado.");
                            //volta o estoque que foi baixado na venda
                            itens.forEach(VendasController::voltarEstoque);
                        }
                    }
                    opcao = 0;
                }
            }
        }while (opcao != 0);
  }

    private static void baixarEstoque(Item item){
        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() - item.getQuantidade());
        Produto produtoAlterado = produtoService.update(produto);
        if (produtoAlterado != null) {
            System.out.println("estoque atualizado:" + produtoAlterado);
        } else {
            System.out.println("Erro ao tentar atualizar o estoque. Por favor, contate o adminstrador.");
        }
    }

    private static void voltarEstoque(Item item){
        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() + item.getQuantidade());
        Produto produtoAlterado = produtoService.update(produto);
        if (produtoAlterado != null) {
            System.out.println("estoque estornado:" + produtoAlterado);
        } else {
            System.out.println("Erro ao tentar estornar o estoque. Por favor, contate o adminstrador.");
        }
    }
}
