package br.com.elton.blackjack.model;

/**
 * Enumeração dos valores das cartas no Blackjack (21).
 */
public enum ValorCarta {
    AS(11, "A"),
    DOIS(2, "2"),
    TRES(3, "3"),
    QUATRO(4, "4"),
    CINCO(5, "5"),
    SEIS(6, "6"),
    SETE(7, "7"),
    OITO(8, "8"),
    NOVE(9, "9"),
    DEZ(10, "10"),
    VALETE(10, "J"),
    DAMA(10, "Q"),
    REI(10, "K");

    private final int valorBase;
    private final String rotulo;

    ValorCarta(int valorBase, String rotulo) {
        this.valorBase = valorBase;
        this.rotulo = rotulo;
    }

    public int getValorBase() {
        return valorBase;
    }

    public String getRotulo() {
        return rotulo;
    }

    public boolean isAs() {
        return this == AS;
    }

    @Override
    public String toString() {
        return rotulo;
    }
}
