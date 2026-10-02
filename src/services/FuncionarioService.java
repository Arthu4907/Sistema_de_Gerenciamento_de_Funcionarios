package services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import entities.Funcionario;
import repository.FuncionarioRepository;

// O service guarda as regras do sistema.
// Ele NÃO imprime nada e NÃO lê do teclado: só recebe dados e devolve resultados.
// Para guardar os dados no arquivo, ele usa o repository.
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final List<Funcionario> funcionarios;

    // Ao criar o service, os funcionários salvos no arquivo são carregados
    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
        this.funcionarios = new ArrayList<>(repository.carregar());
    }

    public int quantidade() {
        return funcionarios.size();
    }

    public boolean estaVazio() {
        return funcionarios.isEmpty();
    }

    public boolean existeId(int id) {
        return funcionarios.stream().anyMatch(f -> f.getId() == id);
    }

    public void cadastrar(Funcionario funcionario) {
        if (funcionario.getId() <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }
        if (existeId(funcionario.getId())) {
            throw new IllegalArgumentException("Já existe um funcionário com esse ID.");
        }
        validarNome(funcionario.getNome());
        validarSalario(funcionario.getSalario());

        funcionarios.add(funcionario);
        repository.salvar(funcionarios);
    }

    // Devolve uma cópia que não pode ser alterada, para ninguém mexer na lista por fora
    public List<Funcionario> listar() {
        return List.copyOf(funcionarios);
    }

    public Optional<Funcionario> buscarPorId(int id) {
        return funcionarios.stream()
                .filter(f -> f.getId() == id)
                .findFirst();
    }

    // Busca por parte do nome, sem diferenciar maiúsculas e minúsculas
    public List<Funcionario> buscarPorNome(String trecho) {
        String busca = trecho.trim().toLowerCase();
        return funcionarios.stream()
                .filter(f -> f.getNome().toLowerCase().contains(busca))
                .toList();
    }

    // Retorna true se removeu, false se não encontrou
    public boolean remover(int id) {
        boolean removido = funcionarios.removeIf(f -> f.getId() == id);
        if (removido) {
            repository.salvar(funcionarios);
        }
        return removido;
    }

    // Retorna true se atualizou, false se não encontrou
    public boolean atualizarSalario(int id, double novoSalario) {
        validarSalario(novoSalario);

        Optional<Funcionario> funcionario = buscarPorId(id);
        if (funcionario.isEmpty()) {
            return false;
        }

        funcionario.get().setSalario(novoSalario);
        repository.salvar(funcionarios);
        return true;
    }

    public double salarioMedio() {
        return funcionarios.stream()
                .mapToDouble(Funcionario::getSalario)
                .average()
                .orElse(0.0);
    }

    public Optional<Funcionario> maiorSalario() {
        return funcionarios.stream()
                .max(Comparator.comparingDouble(Funcionario::getSalario));
    }

    public List<Funcionario> ordenarPorNome() {
        return funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public List<Funcionario> ordenarPorSalario() {
        return funcionarios.stream()
                .sorted(Comparator.comparingDouble(Funcionario::getSalario).reversed())
                .toList();
    }

    // ----- Validações (usadas no cadastro e na atualização) -----

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ficar vazio.");
        }
        // O ";" é usado para separar os dados no arquivo, então não pode estar no nome
        if (nome.contains(";")) {
            throw new IllegalArgumentException("O nome não pode conter o caractere ';'.");
        }
    }

    private void validarSalario(double salario) {
        if (salario <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero.");
        }
    }
}