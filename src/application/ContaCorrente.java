package application;

public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void sacar(double valor) throws BancoException {
        //Conta corrente tem que pagar uma taxa de 5 reais
        super.sacar(valor + 5);
        System.out.println("Contas correntes pagam uma taxa de R$ 5,00 por saque.");
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Corrente ===");
        super.imprimirInfosComuns();
    }
}
