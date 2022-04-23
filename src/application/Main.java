package application;

import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static Scanner sc;
    static Banco banco;
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        sc = new Scanner(System.in);
        if (operacaoInicial()){
            operacoes(banco);
        }
        sc.close();
    }
    static private boolean operacaoInicial(){
        boolean finalizado = false;
        while (!finalizado){
            System.out.print("Nome do banco: ");
            String nomeBanco = sc.nextLine();
            banco = new Banco(nomeBanco);
            System.out.print("Quantas contas serão cadastrados? ");
            int qntd = sc.nextInt();
            sc.nextLine();
            for (int i = 0; i < qntd; i++) {
                cadastrarConta(banco);
            }
            finalizado = true;
        }
        return finalizado;
    }
    static private void operacoes(Banco banco){
        boolean logado = true;
        while (logado){
            System.out.println("\nQual operação deseja realizar?");
            System.out.println("1 - Saque");
            System.out.println("2 - Depósito");
            System.out.println("3 - Transferência");
            System.out.println("4 - Cadastrar conta");
            System.out.println("5 - Logout\n");
            int operacao = sc.nextInt();
            sc.nextLine();
            if (operacao == 1){
                sacar(banco);
            }else if (operacao == 2){
                depositar(banco);
            }else if (operacao == 3){
                transferir(banco);
            }else if (operacao == 4){
                cadastrarConta(banco);
            } else if (operacao == 5){
                logado = false;
            }
        }
        System.out.println("Você foi deslogado.");
    }
    static private void transferir(Banco banco){
        System.out.println("\n=== Lista de clientes ===");
        for (Conta conta : banco.getContas()) {
            System.out.println(conta.numero + " -> " + conta.cliente.getNome());
        }
        System.out.print("\nQual número da conta que irá transferir o dinheiro? ");
        int numeroContaSaida = sc.nextInt();
        Conta contaSaida = banco.getContas().stream().filter(x -> x.numero == numeroContaSaida).findFirst().orElse(null);
        System.out.print("Qual número da conta que irá receber o dinheiro? ");
        int numeroContaEntrada = sc.nextInt();
        Conta contaEntrada = banco.getContas().stream().filter(x -> x.numero == numeroContaEntrada).findFirst().orElse(null);
        System.out.print("Valor da transferência: ");
        double valor = sc.nextDouble();
        sc.nextLine();
        contaSaida.transferir(valor, contaEntrada);
        contaSaida.imprimirInfosComuns();
        contaEntrada.imprimirInfosComuns();
    }
    static private void depositar(Banco banco){
        System.out.println("\n=== Lista de clientes ===");
        for (Conta conta : banco.getContas()) {
            System.out.println(conta.numero + " -> " + conta.cliente.getNome());
        }
        System.out.print("\nQual número da conta que irá depositar o dinheiro? ");
        int numeroConta = sc.nextInt();
        Conta conta = banco.getContas().stream().filter(x -> x.numero == numeroConta).findFirst().orElse(null);
        System.out.print("Valor do depósito: ");
        double valor = sc.nextDouble();
        sc.nextLine();
        conta.depositar(valor);
        conta.imprimirInfosComuns();
    }
    static private void sacar(Banco banco){
        System.out.println("\n=== Lista de clientes ===");
        for (Conta conta : banco.getContas()) {
            System.out.println(conta.numero + " -> " + conta.cliente.getNome());
        }
        System.out.print("\nQual número da conta que irá sacar o dinheiro? ");
        int numeroConta = sc.nextInt();
        Conta conta = banco.getContas().stream().filter(x -> x.numero == numeroConta).findFirst().orElse(null);
        System.out.print("Valor do saque: ");
        double valor = sc.nextDouble();
        sc.nextLine();
        conta.sacar(valor);
        conta.imprimirInfosComuns();
    }
    static private void cadastrarConta(Banco banco){
        System.out.print("\nNome do cliente: ");
        Conta conta;
        String nome = sc.nextLine();
        System.out.print("Tipo de conta (corrente/poupança): ");
        String tipoConta = sc.nextLine().toLowerCase();
        if (tipoConta.equals("corrente")){
            conta = new ContaCorrente(new Cliente(nome));
        }else if (tipoConta.equals("poupança")){
            conta = new ContaPoupanca(new Cliente(nome));
        }else {
            conta = null;
        }
        banco.adicionarConta(conta);
    }
}
