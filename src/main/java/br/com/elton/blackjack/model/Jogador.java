package br.com.elton.blackjack.model;

/**
 * Classe abstrata base para os participantes do jogo (Humano e Dealer).
 */
public abstract class Jogador {
    private final String nome;
    private final Mao mao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new Mao();
    }

    public String getNome() {
        return nome;
    }

    public Mao getMao() {
        return mao;
    }

    public void receberCarta(Carta carta) {
        mao.adicionarCarta(carta);
    }

    public void limparMao() {
        mao.limpar();
    }

    public int getPontuacao() {
        return mao.calcularPontuacao();
    }

    public boolean isEstourou() {
        return mao.isEstourou();
    }

    public boolean isBlackjack() {
        return mao.isBlackjack();
    }
}
