package br.com.elton.blackjack.model;

/**
 * Enumeração representando os quatro naipes de um baralho tradicional.
 */
public enum Naipe {
    ESPADAS("♠", "Preto"),
    COPAS("♥", "Vermelho"),
    OUROS("♦", "Vermelho"),
    PAUS("♣", "Preto");

    private final String simbolo;
    private final String cor;

    Naipe(String simbolo, String cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getCor() {
        return cor;
    }

    @Override
    public String toString() {
        return simbolo;
    }
}
