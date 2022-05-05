package application;

import java.util.Scanner;

public interface IConta {
    void sacar(double valor) throws BancoException;
    void depositar(double valor);
    void transferir(double valor, IConta contaDestino) throws BancoException;
    void imprimirExtrato();
}
