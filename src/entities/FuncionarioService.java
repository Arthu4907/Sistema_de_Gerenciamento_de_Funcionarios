package src.entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FuncionarioService {

    private List<Funcionario> funcionarios = new ArrayList<>();

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    // Verifica se existem funcionários cadastrados
    private boolean verificarFuncionarios() {

        if (funcionarios.isEmpty()) {
            System.out.println("Não há funcionários cadastrados.");
            return false;
        }

        return true;
    }

   

    // Cadastrar funcionário verificando ID repetido
    public void cadastrar(Funcionario funcionario) {

        if (existeId(funcionario.getId())) {
            throw new IllegalArgumentException(
                    "Já existe um funcionário com esse ID."
            );
        }

        funcionarios.add(funcionario);

        System.out.println("Funcionário cadastrado com sucesso!");
    }

    // Verificar se ID já existe
    public boolean existeId(Integer id) {

        return funcionarios.stream()
                .anyMatch(f -> f.getId().equals(id));
    }

    // Listar funcionários
    public void listarFuncionarios() {

        if (!verificarFuncionarios()) {
            return;
        }

        funcionarios.forEach(System.out::println);
    }

    // Remover funcionário pelo ID
    public void removerFuncionario(Integer id) {

        if (!verificarFuncionarios()) {
            return;
        }

        boolean removido = funcionarios.removeIf(
                f -> f.getId().equals(id)
        );

        if (removido) {
            System.out.println("Funcionário removido com sucesso!");
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    // Atualizar salário
    public void atualizarSalary(Integer id, Double salary) {

        if (!verificarFuncionarios()) {
            return;
        }

        boolean encontrado = false;

        for (Funcionario f : funcionarios) {

            if (f.getId().equals(id)) {

                f.setSalary(salary);
                encontrado = true;

                System.out.println(
                        "Salário atualizado com sucesso!"
                );

                break;
            }
        }

        if (!encontrado) {
            System.out.println("Funcionário não encontrado.");
        }
    }

    // Buscar funcionário por ID
    public void buscarPorId(Integer id) {

        if (!verificarFuncionarios()) {
            return;
        }

        funcionarios.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        f -> System.out.println(f),
                        () -> System.out.println(
                                "Funcionário não encontrado!"
                        )
                );
    }

    // Buscar funcionário por nome
    public void filtroPorNome(String name) {

        if (!verificarFuncionarios()) {
            return;
        }

        funcionarios.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(
                        f -> System.out.println(f),
                        () -> System.out.println(
                                "Funcionário não encontrado!"
                        )
                );
    }

    // Calcular salário médio
    public void salaryMedio() {

        if (!verificarFuncionarios()) {
            return;
        }

        Double total = funcionarios.stream()
                .map(Funcionario::getSalary)
                .reduce(0.0, Double::sum);

        double media = total / funcionarios.size();

        System.out.println(
                "Média salarial: "
                + String.format("%.2f", media)
        );
    }

    // Mostrar funcionário com maior salário
    public void maiorSalary() {

        if (!verificarFuncionarios()) {
            return;
        }

        Funcionario maior = funcionarios.stream()
                .max(
                        Comparator.comparing(
                                Funcionario::getSalary
                        )
                )
                .get();

        System.out.println(maior);
    }

    // Ordenar por nome
    public void ordenarPorNome() {

        if (!verificarFuncionarios()) {
            return;
        }

        funcionarios.stream()
                .sorted(
                        Comparator.comparing(
                                Funcionario::getName
                        )
                )
                .forEach(System.out::println);
    }

    // Ordenar por salário
    public void ordenarPorSalary() {

        if (!verificarFuncionarios()) {
            return;
        }

        funcionarios.stream()
                .sorted(
                        Comparator.comparing(
                                Funcionario::getSalary
                        ).reversed()
                )
                .forEach(System.out::println);
    }


   
    public static int lerInteiro(Scanner sc) {

    while (!sc.hasNextInt()) {
        System.out.print("Entrada inválida! Digite apenas números: ");
        sc.next();
    }

    return sc.nextInt();
}
    public double lerDouble(Scanner sc) {

        while (!sc.hasNextDouble()) {

        System.out.print("Entrada inválida! Digite apenas números: ");

        sc.next();
    }

    return sc.nextDouble();
}
}