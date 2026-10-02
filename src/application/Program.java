package application;

import java.util.List;
import java.util.Scanner;

import entities.Funcionario;
import repository.FuncionarioRepository;
import services.FuncionarioService;
import util.Entrada;

public class Program {

    // Nome do arquivo onde os funcionários ficam salvos.
    // Ele é criado na pasta de onde o programa é executado.
    private static final String ARQUIVO = "funcionarios.csv";

    private static FuncionarioService service;
    private static Entrada entrada;

    public static void main(String[] args) {

        try {
            service = new FuncionarioService(new FuncionarioRepository(ARQUIVO));
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        System.out.println(service.quantidade() + " funcionário(s) carregado(s) do arquivo.");

        Scanner sc = new Scanner(System.in);
        entrada = new Entrada(sc);

        int opcao;

        do {
            mostrarMenu();
            opcao = entrada.lerInteiro("Escolha uma opção: ");
            System.out.println();

            // Da opção 2 em diante, não faz sentido continuar com a lista vazia
            if (opcao >= 2 && opcao <= 10 && service.estaVazio()) {
                System.out.println("Não há funcionários cadastrados.");
                continue;
            }

            // O try pega erros na hora de salvar no arquivo
            try {
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> imprimirLista("LISTA DE FUNCIONÁRIOS", service.listar());
                    case 3 -> buscarPorId();
                    case 4 -> buscarPorNome();
                    case 5 -> remover();
                    case 6 -> atualizarSalario();
                    case 7 -> System.out.printf("Média salarial: R$ %.2f%n", service.salarioMedio());
                    case 8 -> mostrarMaiorSalario();
                    case 9 -> imprimirLista("LISTA ORDENADA POR NOME", service.ordenarPorNome());
                    case 10 -> imprimirLista("LISTA ORDENADA POR SALÁRIO", service.ordenarPorSalario());
                    case 0 -> System.out.println("FIM DO PROGRAMA.");
                    default -> System.out.println("Opção inválida! Escolha um número de 0 a 10.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== SISTEMA DE CADASTRO DE FUNCIONÁRIOS =====");
        System.out.println("1 - Cadastrar funcionário");
        System.out.println("2 - Listar funcionários");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Buscar por nome");
        System.out.println("5 - Remover funcionário");
        System.out.println("6 - Atualizar salário");
        System.out.println("7 - Calcular salário médio");
        System.out.println("8 - Mostrar maior salário");
        System.out.println("9 - Ordenar por nome");
        System.out.println("10 - Ordenar por salário");
        System.out.println("0 - Sair");
    }

    // ===== AÇÕES DO MENU =====

    private static void cadastrar() {
        int quantidade = entrada.lerInteiroPositivo("Quantos funcionários deseja cadastrar? ");

        for (int i = 1; i <= quantidade; i++) {
            System.out.println();
            System.out.println("Funcionário " + i + " de " + quantidade);

            int id = entrada.lerInteiroPositivo("ID: ");
            while (service.existeId(id)) {
                System.out.println("Erro! Esse ID já está cadastrado.");
                id = entrada.lerInteiroPositivo("ID: ");
            }

            String nome = entrada.lerTexto("Nome: ");
            while (nome.contains(";")) {
                System.out.println("O nome não pode conter o caractere ';'.");
                nome = entrada.lerTexto("Nome: ");
            }
            double salario = entrada.lerDoublePositivo("Salário: ");

            try {
                service.cadastrar(new Funcionario(id, nome, salario));
                System.out.println("Funcionário cadastrado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void buscarPorId() {
        int id = entrada.lerInteiro("Digite o ID que deseja buscar: ");

        service.buscarPorId(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Funcionário não encontrado."));
    }

    private static void buscarPorNome() {
        String nome = entrada.lerTexto("Digite o nome (ou parte dele): ");
        List<Funcionario> encontrados = service.buscarPorNome(nome);

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
        } else {
            imprimirLista("RESULTADO DA BUSCA", encontrados);
        }
    }

    private static void remover() {
        int id = entrada.lerInteiro("Digite o ID do funcionário que deseja remover: ");

        if (service.remover(id)) {
            System.out.println("Funcionário removido com sucesso!");
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    private static void atualizarSalario() {
        int id = entrada.lerInteiro("Digite o ID do funcionário: ");

        if (!service.existeId(id)) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        double novoSalario = entrada.lerDoublePositivo("Novo salário: ");

        try {
            service.atualizarSalario(id, novoSalario);
            System.out.println("Salário atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void mostrarMaiorSalario() {
        System.out.println("FUNCIONÁRIO COM O MAIOR SALÁRIO");
        service.maiorSalario().ifPresent(System.out::println);
    }

    private static void imprimirLista(String titulo, List<Funcionario> lista) {
        System.out.println(titulo);
        lista.forEach(System.out::println);
    }
}