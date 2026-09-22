package src.application;

import java.util.Locale;
import java.util.Scanner;
import src.entities.Funcionario;
import src.entities.FuncionarioService;

public class Program {
    public static void main(String[] args) {
        
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);  

        FuncionarioService servise = new FuncionarioService();
        Funcionario funcionario = new Funcionario();

        int escolha = -1;

   while(escolha != 0){
   
    System.out.println();

    System.out.println("Sistema de Cadastro de Funcionarios");
     
     System.out.println("1 - Cadastrar funcionario");
     System.out.println("2 -  Listar funcionários");
     System.out.println("3 - Buscar por ID");
     System.out.println("4 - Buscar por nome");
     System.out.println("5 - Remover funcionário");
     System.out.println("6 -  Atualizar salário");
     System.out.println("7 - Calcular salário médio");
     System.out.println("8 - Mostrar maior salário");
     System.out.println("9 - Ordenar por nome");
     System.out.println("10 - Ordenar por salário");
     System.out.println("0 - Sair");
     System.out.print("Escolha uma opção: ");
     escolha = servise.lerInteiro(sc);
     System.out.println();

    while(escolha <0 || escolha > 10){
        System.out.print("Valor invalido! Digite um valor valor valido:");
     escolha = servise.lerInteiro(sc);
     System.out.println();
    }
    if(escolha == 1){
        System.out.print("Deseja cadastrar quantos funcionarios? ");
        int quantidade_de_funcionarios = servise.lerInteiro(sc);

        for(int i=0; i< quantidade_de_funcionarios;i++){
           
            System.out.println();
            int id;

            while(true){
                 System.out.print("Digite o id do " + (i+1) + " funcionario:");
                 id = servise.lerInteiro(sc);

            if(!servise.existeId(id)){
                break;
            }
             System.out.println("Erro! Esse ID já está cadastrado.");
            }

            sc.nextLine();
        
            System.out.print("Digite o nome do funcionario: ");
            String name = sc.nextLine();

            System.out.print("Digite o salário do funcionário: ");
            double salary = servise.lerDouble(sc);

            while(salary <= 0){
                System.out.println("Error! O salário tem que ser um valor valido.");
                 System.out.print("Digite o salário do funcionário novamente: ");
                salary = servise.lerDouble(sc);
            }

            Funcionario func = new Funcionario(name, id, salary);
            servise.cadastrar(func);
           
            
        }
        
            System.out.println();
        } 
        else if(escolha == 2){
            System.out.println();
            System.out.println("LISTA DE FUNCIONÁRIOS");
            servise.listarFuncionarios();
    }
        else if(escolha == 3){
            System.out.println();
            System.out.print("Digite o Id que deseja buscar: ");
            int idBuscar = servise.lerInteiro(sc);

            servise.buscarPorId(idBuscar);
        }
        else if(escolha == 4){
            sc.nextLine();
            System.out.println();
            System.out.print("Digite o nome que deseja buscar: ");
            String nameBusca = sc.nextLine();

            servise.filtroPorNome(nameBusca);
        }
        else if(escolha == 5){
            System.out.println();
            System.out.print("Digite o Id do funcionário que deseja remover do Sistema: ");
            int remove = servise.lerInteiro(sc);

            servise.removerFuncionario(remove);
        }
        else if(escolha == 6){
            System.out.println();
            System.out.print("Digite o ID do funcionário que deseja mudar o salário: ");
            int idSalary = servise.lerInteiro(sc);
            System.out.print("Digite o novo salário: ");
            double newSalary = servise.lerDouble(sc);

            servise.atualizarSalary(idSalary, newSalary);
        }
        else if(escolha == 7){
            System.out.println();
            servise.salaryMedio();
        }
        else if(escolha == 8){
            System.out.println();
            System.out.println("FUNCIONÁRIO COM O MAIOR SALARIO");
            servise.maiorSalary();
        }
        else if(escolha == 9){
            System.out.println();
            System.out.println("LISTA DE FUNCIONÁRIOS ORDENADA POR NOME");
            servise.ordenarPorNome();
        }
        else if(escolha == 10){
            System.out.println();
            System.out.println("LISTA DE FUNCIONÁRIOS ORDENADA POR SALÁRIO");
            servise.ordenarPorSalary();
        }


   }
   System.out.println();
   System.out.println("FIM DO PROGRAMA.");










   



    


    

    }
}
