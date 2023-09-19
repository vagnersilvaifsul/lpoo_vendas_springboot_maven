package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.pedidos;

import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes.Cliente;
import br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes.ClienteService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class PedidoController {
	
	private static final Scanner input = new Scanner(System.in);
    private static ClienteService clienteService;
    private static PedidoService pedidoService;

    //Injeção de dependência (Não utilizou @Autowired porque o Springboot não injeta a dependência. Afinal, estamos adaptando um framework web para modo texto)
    public PedidoController(ClienteService clienteService, PedidoService pedidoService){
        PedidoController.clienteService = clienteService;
        PedidoController.pedidoService = pedidoService;
    }
	
    public static void main(String[] args) {
        int opcao;
        do{
            System.out.println("\n\n******** Pedidos ********");
            System.out.print(
                """
                    1. Checkout do pedido
                    2. Entregar Pedido
                    3. Excluir Pedido
                    4. Lista todos os pedidos inativos
                    5. Lista todos os pedidos ativos
                    6. Lista todos os pedidos por período
                    7. Listar pedidos de um cliente
                    Digite a opção (0 para sair):\s"""
            );
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1 -> checkout();
                case 2 -> entregar();
                case 3, 4, 5, 6 -> System.out.println("em desenvolvimento " + opcao);
                case 7 -> selectPedidosByIdCliente();
                default -> {
                    if (opcao != 0) System.out.println("Opção inválida.");
                }
            }
        }while (opcao != 0);
    }

    //1
    private static void checkout(){
        System.out.print("Digite o código do pedido: ");
        long id = input.nextLong();
        input.nextLine();
        Pedido pedido = pedidoService.getPedidoById(id);
        if(pedido == null){
            System.out.println("Codigo inexistente.");
        } else {
            System.out.println("\nPedido Pesquisado: " + pedido);
            System.out.println("Deseja fazer o checkout (sim-1/não-2)?");
            input.nextInt();
            input.nextLine();
            pedido.setEstado(Estado.PAGO);
            Pedido pedidoAtualizado = pedidoService.update(pedido);
            if(pedidoAtualizado != null){
                System.out.println("Checkout realizado com sucesso. " + pedidoAtualizado);
            }
        }
    }

    private static void entregar(){
        System.out.print("Digite o código do pedido: ");
        long id = input.nextLong();
        input.nextLine();
        Pedido pedido = pedidoService.getPedidoById(id);
        if(pedido == null){
            System.out.println("Codigo inexistente.");
        } else if(pedido.getEstado() == Estado.PAGO){
            System.out.println("\nPedido Pesquisado: " + pedido);
            System.out.println("Deseja entregar o pedido (sim-1/não-2)?");
            input.nextInt();
            input.nextLine();
            pedido.setEstado(Estado.ENTREGUE);
            Pedido pedidoAtualizado = pedidoService.update(pedido);
            if(pedidoAtualizado != null){
                System.out.println("Pedido entregue com sucesso. " + pedidoAtualizado);
            }
        } else {
            System.out.println("Somente pedidos na situação PAGO podem ser entregues.");
        }
    }
    
    private static void selectPedidosByIdCliente() {
    	System.out.print("Digite o código do cliente: ");
        long id = input.nextLong();
        input.nextLine();
        Cliente cliente = clienteService.getClienteById(id);
        if(cliente == null) {
        	System.out.println("Codigo inexistente.");
        }else {
        	System.out.println("\nCliente Pesquisado: " + cliente);
        	List<Pedido> pedidos = pedidoService.getPedidosByIdCliente(cliente.getId());
        	if(pedidos.isEmpty()) {
        		System.out.println("Não há pedidos para o cliente " + cliente.getNome());
        	}else {
        		System.out.println("Lista de pedidos do o cliente " + cliente.getNome());
        		System.out.println(pedidos);
        	}
        }
        
    }
}
