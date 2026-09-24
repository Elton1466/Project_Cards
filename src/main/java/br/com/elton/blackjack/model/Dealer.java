package br.com.elton.blackjack.model;

/**
 * Representa a banca (Dealer/Mesa) no jogo de Blackjack.
 * Regra: O Dealer é obrigado a pedir cartas até atingir pelo menos 17 pontos.
 */
public class Dealer extends Jogador {

    public Dealer() {
        super("Dealer (Mesa)");
    }

    /**
     * Verifica se o Dealer deve pedir mais uma carta (Soft/Hard <= 16).
     *
     * @return true se pontuação < 17.
     */
    public boolean devePedirCarta() {
        return getPontuacao() < 17;
    }
}
