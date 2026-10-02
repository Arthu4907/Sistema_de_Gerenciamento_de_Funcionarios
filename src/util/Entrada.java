package util;

import java.util.Scanner;

// Esta classe cuida só de ler dados do teclado.
// Ela sempre lê a linha inteira, assim não sobra "enter" perdido no Scanner,
// e repete a pergunta até o usuário digitar um valor válido.
public class Entrada {

    private final Scanner sc;

    public Entrada(Scanner sc) {
        this.sc = sc;
    }

    public String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = sc.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Entrada inválida! O campo não pode ficar vazio.");
        }
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = sc.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
            }
        }
    }

    public int lerInteiroPositivo(String mensagem) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor > 0) {
                return valor;
            }
            System.out.println("O valor deve ser maior que zero.");
        }
    }

    // Aceita tanto vírgula quanto ponto como separador decimal (ex: 2500,50 ou 2500.50)
    public double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = sc.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número.");
            }
        }
    }

    public double lerDoublePositivo(String mensagem) {
        while (true) {
            double valor = lerDouble(mensagem);
            if (valor > 0) {
                return valor;
            }
            System.out.println("O valor deve ser maior que zero.");
        }
    }
}
