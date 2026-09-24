package br.com.elton.blackjack.model;

/**
 * Representa o jogador controlado pelo usuário humano, com gestão de saldo/fichas.
 */
public class Humano extends Jogador {
    private double saldoFichas;
    private double apostaAtual;

    public Humano(String nome, double saldoInicial) {
        super(nome);
        this.saldoFichas = Math.max(0, saldoInicial);
        this.apostaAtual = 0;
    }

    public double getSaldoFichas() {
        return saldoFichas;
    }

    public double getApostaAtual() {
        return apostaAtual;
    }

    public boolean fazerAposta(double valor) {
        if (valor > 0 && valor <= saldoFichas) {
            this.apostaAtual = valor;
            this.saldoFichas -= valor;
            return true;
        }
        return false;
    }

    public void receberRecompensa(double multiplicador) {
        double premio = apostaAtual * multiplicador;
        this.saldoFichas += premio;
        this.apostaAtual = 0;
    }

    public void devolverAposta() {
        this.saldoFichas += apostaAtual;
        this.apostaAtual = 0;
    }

    public void perderAposta() {
        this.apostaAtual = 0;
    }
}
