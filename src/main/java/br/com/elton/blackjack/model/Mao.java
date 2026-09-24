package br.com.elton.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa a mão de cartas de um participante no jogo.
 */
public class Mao {
    private final List<Carta> cartas;

    public Mao() {
        this.cartas = new ArrayList<>();
    }

    public void adicionarCarta(Carta carta) {
        if (carta != null) {
            cartas.add(carta);
        }
    }

    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

    public void limpar() {
        cartas.clear();
    }

    /**
     * Calcula a pontuação da mão ajustando o valor do Ás (11 ou 1) dinamicamente.
     *
     * @return Pontuação otimizada sem estourar 21 se possível.
     */
    public int calcularPontuacao() {
        int pontos = 0;
        int asesCount = 0;

        for (Carta carta : cartas) {
            pontos += carta.getPontuacaoBase();
            if (carta.isAs()) {
                asesCount++;
            }
        }

        // Se estourar 21 e houver Áses contabilizados como 11, reduz cada Ás para 1 (subtrai 10)
        while (pontos > 21 && asesCount > 0) {
            pontos -= 10;
            asesCount--;
        }

        return pontos;
    }

    public boolean isEstourou() {
        return calcularPontuacao() > 21;
    }

    public boolean isBlackjack() {
        return cartas.size() == 2 && calcularPontuacao() == 21;
    }

    public int getTamanho() {
        return cartas.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Carta c : cartas) {
            sb.append(c).append(" ");
        }
        sb.append("(Total: ").append(calcularPontuacao()).append(")");
        return sb.toString();
    }
}
