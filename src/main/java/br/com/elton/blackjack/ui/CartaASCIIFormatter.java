package br.com.elton.blackjack.ui;

import br.com.elton.blackjack.model.Carta;

import java.util.List;

/**
 * Utilitário responsável por formatar e desenhar cartas em ASCII Art para o terminal.
 */
public class CartaASCIIFormatter {

    /**
     * Gera uma representação visual em ASCII Art lado a lado para uma lista de cartas.
     *
     * @param cartas Lista de cartas a serem desenhadas.
     * @param ocultarSegunda Se true, a 2ª carta será desenhada como oculta [??].
     * @return String formatada linha por linha com as cartas em ASCII Art.
     */
    public static String formatarMaoASCII(List<Carta> cartas, boolean ocultarSegunda) {
        if (cartas == null || cartas.isEmpty()) {
            return "(Nenhuma carta)";
        }

        int tamanho = cartas.size();
        String[] l1 = new String[tamanho];
        String[] l2 = new String[tamanho];
        String[] l3 = new String[tamanho];
        String[] l4 = new String[tamanho];
        String[] l5 = new String[tamanho];

        for (int i = 0; i < tamanho; i++) {
            if (i == 1 && ocultarSegunda) {
                l1[i] = "┌───────┐";
                l2[i] = "│ ?   ? │";
                l3[i] = "│   ?   │";
                l4[i] = "│ ?   ? │";
                l5[i] = "└───────┘";
            } else {
                Carta c = cartas.get(i);
                String val = c.getValorCarta().getRotulo();
                String naipe = c.getNaipe().getSimbolo();

                String valEsquerda = String.format("%-2s", val);
                String valDireita  = String.format("%2s", val);

                l1[i] = "┌───────┐";
                l2[i] = "│ " + valEsquerda + "    │";
                l3[i] = "│   " + naipe + "   │";
                l4[i] = "│    " + valDireita + " │";
                l5[i] = "└───────┘";
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.join(" ", l1)).append("\n");
        sb.append(String.join(" ", l2)).append("\n");
        sb.append(String.join(" ", l3)).append("\n");
        sb.append(String.join(" ", l4)).append("\n");
        sb.append(String.join(" ", l5));

        return sb.toString();
    }
}
