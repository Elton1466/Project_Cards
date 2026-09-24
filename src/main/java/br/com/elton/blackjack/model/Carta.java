package br.com.elton.blackjack.model;

import java.util.Objects;

/**
 * Entidade imutável representando uma carta individual de baralho.
 */
public final class Carta {
    private final Naipe naipe;
    private final ValorCarta valorCarta;

    public Carta(Naipe naipe, ValorCarta valorCarta) {
        this.naipe = Objects.requireNonNull(naipe, "O naipe não pode ser nulo.");
        this.valorCarta = Objects.requireNonNull(valorCarta, "O valor da carta não pode ser nulo.");
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public ValorCarta getValorCarta() {
        return valorCarta;
    }

    public int getPontuacaoBase() {
        return valorCarta.getValorBase();
    }

    public boolean isAs() {
        return valorCarta.isAs();
    }

    @Override
    public String toString() {
        return "[" + valorCarta.getRotulo() + naipe.getSimbolo() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carta carta = (Carta) o;
        return naipe == carta.naipe && valorCarta == carta.valorCarta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(naipe, valorCarta);
    }
}
