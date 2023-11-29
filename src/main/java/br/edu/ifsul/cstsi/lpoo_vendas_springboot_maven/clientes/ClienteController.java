package br.edu.ifsul.cstsi.lpoo_vendas_springboot_maven.clientes;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;
@Controller
public class ClienteController {

    private static final Scanner input = new Scanner(System.in);
    private static ClienteService clienteService;

    //Injeção de dependência
    public ClienteController(ClienteService ClienteService) {
        ClienteController.clienteService = ClienteService;
    }

    public static void main(String[] args) {

        int opcao;
        do {
            System.out.print("\n\"-------  MENU cliente -------\"");
            System.out.print(
                """

                    1. Inserir novo cliente
                    2. Atualizar um cliente
                    3. Excluir um cliente (tornar inativo)
                    4. Ativar ou Desativar um cliente
                    5. Listar todos os clientes
                    6. Buscar cliente pelo código
                    7. Buscar clientes pelo nome
                    8. Buscar clientes pela situação
                    Opção (Zero p/sair):\s""");
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1 -> inserir();
                case 2 -> atualizar();
                case 3 -> excluir();
                case 4 -> ativar();
                case 5 -> selectclientes();
                case 6 -> selectclientesById();
                case 7 -> selectclientesByNome();
                case 8 -> selectClientesBySituacao();
                default -> {
                    if (opcao != 0) System.out.println("Opção inválida.");
                }
            }
        } while (opcao != 0);
    }

    //opção 1
    private static void inserir() {
        Cliente cliente = new Cliente();
        System.out.println("\n++++++ Cadastro de novo Cliente ++++++");
        System.out.print("Digite o nome do cliente: ");
        cliente.setNome(input.nextLine());
        System.out.print("\nDigite o sobrenome do cliente: ");
        cliente.setSobrenome(input.nextLine());
        cliente.setSituacao(true);
        System.out.println("cliente salvo com sucesso:" + clienteService.insert(cliente));
    }

    //opção 2
    private static void atualizar() {
        System.out.println("\n++++++ Alterar um cliente ++++++");
        Cliente cliente;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do cliente (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else {
                cliente = clienteService.getClienteById(codigo);
                if (cliente == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println("Nome: " + cliente.getNome());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                        input.nextLine();
                        System.out.println("Digite o novo nome do cliente: ");
                        cliente.setNome(input.nextLine());
                    }
                    System.out.println("Sobrenome: " + cliente.getSobrenome());
                    System.out.print("Alterar? (0-sim/1-não) ");
                    if(input.nextInt() == 0){
                        input.nextLine();
                        System.out.print("Digite o novo sobrenome do cliente: ");
                        cliente.setSobrenome(input.nextLine());
                    }
                    cliente.setSituacao(true);
                    if(clienteService.update(cliente) != null) {
                        System.out.println("cliente atualizado com sucesso. " + clienteService.getClienteById(cliente.getId()));
                    } else {
                        System.out.println("Não foi possível atualizar este cliente. Por favor, contate o administrador.");
                    }

                    opcao = 1;
                }
            }
        } while (opcao != 0);
    }

    private static void excluir() {
        System.out.println("\n++++++ Excluir um cliente ++++++");
        Cliente cliente;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do cliente (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if(codigo > 0){
                cliente = clienteService.getClienteById(codigo);
                if (cliente == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(cliente);
                    System.out.print("Excluir este cliente? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (0-sim/1-não) ");
                        cliente.setSituacao(false);
                        input.nextLine();
                        clienteService.delete(cliente.getId());
                        System.out.println("cliente excluído com sucesso:" + cliente);
                    }

                }
            } else {
                System.out.println("Digite um código válido!!!");
            }
        } while (opcao != 0);
    }

    private static void ativar(){
        System.out.println("\n++++++ Ativar/Desativar um cliente ++++++");
        Cliente cliente;
        int opcao = 0;
        do {
            System.out.print("\nDigite o código do cliente (Zero p/sair): ");
            long codigo = input.nextLong();
            input.nextLine();
            if (codigo == 0) {
                opcao = 0;
            } else if(codigo > 0){
                cliente = clienteService.getClienteById(codigo);
                if (cliente == null) {
                    System.out.println("Código inválido.");
                } else {
                    System.out.println(cliente);
                    System.out.print("Ativar/Desativar este cliente? (0-sim/1-não) ");
                    if (input.nextInt() == 0) {
                        input.nextLine();
                        System.out.print("Tem certeza disso? (0-sim/1-não) ");
                        cliente.setSituacao(!cliente.getSituacao());
                        input.nextLine();
                        if(clienteService.update(cliente) != null) {
                            System.out.println("Situação do cliente alterada com sucesso: " + clienteService.getClienteById(codigo));
                        } else {
                            System.out.println("Não foi possível ativar/desativar este cliente. Por favor, contate o administrador.");
                        }
                    }

                }
            } else {
                System.out.println("Digite um código válido!!!");
            }
        } while (opcao != 0);
    }

    //opção 3
    private static void selectclientes() {
        System.out.println("\nLista de clientes cadastrados no banco de dados:\n" + clienteService.getClientes());
    }

    //opção 4
    private static void selectclientesById() {
        System.out.print("\nDigite o código do cliente: ");
        Cliente cliente = clienteService.getClienteById(input.nextLong());
        input.nextLine();
        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("Código não localizado.");
        }
    }

    //opção 5
    private static void selectclientesByNome() {
        System.out.print("Digite o nome do cliente: ");
        String nome = input.next();
        System.out.println("Chave de pesquisa: " + nome);
        List<Cliente> clientes = clienteService.getClientesByNome(nome + "%");
        if (clientes.isEmpty()) {
            System.out.println("Não há registros correspondentes para: " + nome);
        } else {
            System.out.println(clientes);
        }
    }

    //opção 6
    private static void selectClientesBySituacao() {
        System.out.print("Escolha uma das situações (0-inativo/1-ativo): ");
        int situacao = input.nextInt();
        input.nextLine();
        List<Cliente> clientes;
        switch (situacao) {
            case 0 -> {
                clientes = clienteService.getClientesBySituacao(false);
                System.out.println("clientes na situação INATIVO:\n " + clientes);
            }
            case 1 -> {
                clientes = clienteService.getClientesBySituacao(true);
                System.out.println("clientes na situação ATIVO:\n " + clientes);
            }
        }
    }

}//fim classe
