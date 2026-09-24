package br.com.elton.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um baralho completo de 52 cartas com embaralhamento e compra.
 */
public class Baralho {
    private final List<Carta> cartas;

    public Baralho() {
        this.cartas = new ArrayList<>();
        reiniciar();
    }

    /**
     * Reinicializa o baralho com as 52 cartas padronizadas e embaralha.
     */
    public final void reiniciar() {
        cartas.clear();
        for (Naipe naipe : Naipe.values()) {
            for (ValorCarta valor : ValorCarta.values()) {
                cartas.add(new Carta(naipe, valor));
            }
        }
        embaralhar();
    }

    /**
     * Embaralha as cartas disponíveis.
     */
    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    /**
     * Compra (remove) a carta do topo do baralho.
     * Se estiver vazio, o baralho é automaticamente reinicializado e embaralhado.
     *
     * @return Carta comprada.
     */
    public Carta comprarCarta() {
        if (cartas.isEmpty()) {
            reiniciar();
        }
        return cartas.remove(cartas.size() - 1);
    }

    public int getCartasRestantes() {
        return cartas.size();
    }
}
