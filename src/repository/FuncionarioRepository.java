package repository;

import entities.Funcionario;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// Esta classe cuida só de salvar e carregar os funcionários de um arquivo.
// Cada funcionário vira uma linha no arquivo, assim:
//     1;Ana Souza;2500.5
//     2;Carlos Lima;4000.0
public class FuncionarioRepository {

    private static final String SEPARADOR = ";";

    private final Path arquivo;

    public FuncionarioRepository(String caminhoDoArquivo) {
        this.arquivo = Path.of(caminhoDoArquivo);
    }

    // Lê o arquivo e devolve a lista de funcionários.
    // Se o arquivo ainda não existe (primeira vez rodando), devolve lista vazia.
    public List<Funcionario> carregar() {
        List<Funcionario> lista = new ArrayList<>();

        if (!Files.exists(arquivo)) {
            return lista;
        }

        try {
            List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);

            for (String linha : linhas) {
                if (linha.isBlank()) {
                    continue;
                }

                String[] partes = linha.split(SEPARADOR);

                // Se alguma linha estiver estragada, ela é ignorada
                // em vez de travar o programa inteiro
                try {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    double salario = Double.parseDouble(partes[2]);
                    lista.add(new Funcionario(id, nome, salario));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // linha inválida: pula para a próxima
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler o arquivo " + arquivo + ".");
        }

        return lista;
    }

    // Reescreve o arquivo inteiro com a lista atual
    public void salvar(List<Funcionario> funcionarios) {
        List<String> linhas = new ArrayList<>();

        for (Funcionario f : funcionarios) {
            linhas.add(f.getId() + SEPARADOR + f.getNome() + SEPARADOR + f.getSalario());
        }

        try {
            Files.write(arquivo, linhas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível salvar no arquivo " + arquivo + ".");
        }
        System.out.println();
    }
}
