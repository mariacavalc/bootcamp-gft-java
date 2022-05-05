package application;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static Scanner sc;
    static Banco banco;
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        sc = new Scanner(System.in);

        boolean operacaoInicialFinalizada = false;
        while (!operacaoInicialFinalizada){
            try {
                operacaoInicial();
                operacaoInicialFinalizada = true;
            } catch (BancoException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException | InputMismatchException e){
                System.out.println("Valor inválido.\n");
            }
        }

        while (true){
            try {
                if (!operacoes(banco)){
                    break;
                }
            }catch (BancoException e){
                System.out.println(e.getMessage());
            }catch (NumberFormatException | InputMismatchException e){
                System.out.println("Valor inválido");
            }
        }
        sc.close();
    }
    static private void operacaoInicial() throws BancoException {
        System.out.print("Nome do banco: ");
        String nomeBanco = sc.nextLine();
        if (nomeBanco.isBlank() || nomeBanco.isEmpty()){
            throw new BancoException("Nome inválido.\n");
        }
        banco = new Banco(nomeBanco);
        System.out.print("Quantas contas serão cadastrados? ");
        String qntdStr = sc.nextLine();
        int qntd = Integer.parseInt(qntdStr);
        if (qntd == 0){
            throw new BancoException("Valor inválido.\n");
        }
        for (int i = 0; i < qntd; i++) {
            cadastrarConta(banco);
        }
    }
    static private boolean operacoes(Banco banco) throws BancoException {
        System.out.println("\nQual operação deseja realizar?");
        System.out.println("1 - Saque");
        System.out.println("2 - Depósito");
        System.out.println("3 - Transferência");
        System.out.println("4 - Cadastrar conta");
        System.out.println("5 - Logout\n");
        String operacao = sc.nextLine();
        switch (operacao) {
            case "1":
                sacar(banco);
                break;
            case "2":
                depositar(banco);
                break;
            case "3":
                transferir(banco);
                break;
            case "4":
                cadastrarConta(banco);
                break;
            case "5":
                System.out.println("Você foi deslogado(a).");
                return false;
            default:
                throw new BancoException("Operação inválida.");
        }
        return true;
    }
    static private void transferir(Banco banco) throws BancoException {
        System.out.println("\n=== Lista de clientes ===");
        for (Conta conta : banco.getContas()) {
            System.out.println(conta.numero + " -> " + conta.cliente.getNome());
        }
        System.out.print("\nQual número da conta que irá transferir o dinheiro? ");
        Conta contaSaida = localizarConta();
        System.out.print("Qual número da conta que irá receber o dinheiro? ");
        Conta contaEntrada = localizarConta();
        System.out.print("Valor da transferência: ");
        double valor = sc.nextDouble();
        sc.nextLine();
        contaSaida.transferir(valor, contaEntrada);
        contaSaida.imprimirInfosComuns();
        contaEntrada.imprimirInfosComuns();
    }
    static private void depositar(Banco banco) throws BancoException {
        System.out.println("\n=== Lista de clientes ===");
        for (Conta conta : banco.getContas()) {
            System.out.println(conta.numero + " -> " + conta.cliente.getNome());
        }
        System.out.print("\nQual número da conta que irá depositar o dinheiro? ");
        Conta conta = localizarConta();
        System.out.print("Valor do depósito: ");
        double valor = sc.nextDouble();
        sc.nextLine();
        conta.depositar(valor);
        conta.imprimirInfosComuns();
    }
    static private void sacar(Banco banco) throws BancoException {
        System.out.println("\n=== Lista de clientes ===");
        for (Conta conta : banco.getContas()) {
            System.out.println(conta.numero + " -> " + conta.cliente.getNome());
        }
        System.out.print("\nQual número da conta que irá sacar o dinheiro? ");
        Conta conta = localizarConta();
        System.out.print("Valor do saque: ");
        double valor = sc.nextDouble();
        sc.nextLine();
        conta.sacar(valor);
        conta.imprimirInfosComuns();
    }
    static private void cadastrarConta(Banco banco) throws BancoException {
        System.out.print("\nNome do cliente: ");
        Conta conta;
        String nome = sc.nextLine();
        if (nome.isEmpty() || nome.isBlank() || nome.matches(".*[0-9].*")){
            throw new BancoException("Nome inválido.\n");
        }
        System.out.print("Tipo de conta (corrente/poupança): ");
        String tipoConta = sc.nextLine().toLowerCase();
        if (tipoConta.equals("corrente")){
            conta = new ContaCorrente(new Cliente(nome));
        }else if (tipoConta.equals("poupança")){
            conta = new ContaPoupanca(new Cliente(nome));
        }else {
            throw new BancoException("Tipo de conta inválido.\n");
        }
        banco.adicionarConta(conta);
    }
    static private Conta localizarConta() throws BancoException {
        String numeroConta = sc.nextLine();
        Conta conta = banco.getContas().stream().filter(x -> x.numero == Integer.parseInt(numeroConta)).findFirst().orElse(null);
        if (conta != null){
            return conta;
        }else {
            throw new BancoException("Conta inválida.");
        }
    }
}
