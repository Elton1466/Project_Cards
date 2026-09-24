package br.com.elton.blackjack.ui;

import br.com.elton.blackjack.model.Carta;
import br.com.elton.blackjack.model.Dealer;
import br.com.elton.blackjack.model.Humano;
import br.com.elton.blackjack.service.JogoService.ResultadoJogador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Interface de console (CLI) adaptada para modo Multiplayer com renderização ASCII Art das cartas.
 */
public class ConsoleUI {

    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void exibirCabecalho() {
        System.out.println("==================================================================");
        System.out.println("     🃏 JOGO DE BLACKJACK (21) MULTIPLAYER - CASSINO JAVA 🃏      ");
        System.out.println("==================================================================");
        System.out.println(" Modo: 2 Jogadores Humanos contra a Mesa (Dealer)");
        System.out.println(" Regras:");
        System.out.println("  • Alcance 21 pontos ou chegue o mais próximo sem estourar!");
        System.out.println("  • Ás vale 11 ou 1 ponto (ajustado automaticamente).");
        System.out.println("  • Valete (J), Dama (Q) e Rei (K) valem 10 pontos.");
        System.out.println("  • O Dealer para obrigatoriamente se tiver 17 pontos ou mais.");
        System.out.println("==================================================================\n");
    }

    public List<String> solicitarNomesJogadores(int quantidade) {
        List<String> nomes = new ArrayList<>();
        for (int i = 1; i <= quantidade; i++) {
            System.out.printf("Digite o nome do Jogador %d: ", i);
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                nome = "Jogador " + i;
            }
            nomes.add(nome);
        }
        System.out.println();
        return nomes;
    }

    public double solicitarValorAposta(Humano jogador) {
        while (true) {
            System.out.printf("👉 %s (Saldo: R$ %.2f) - Digite a aposta (ou 0 para parar): R$ ",
                    jogador.getNome(), jogador.getSaldoFichas());
            try {
                String linha = scanner.nextLine().trim();
                double aposta = Double.parseDouble(linha);
                if (aposta == 0) return 0;
                if (aposta > 0 && aposta <= jogador.getSaldoFichas()) {
                    return aposta;
                }
                System.out.println("❌ Aposta inválida! O valor deve ser maior que 0 e no máximo seu saldo.\n");
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, insira um valor numérico válido.\n");
            }
        }
    }

    public void exibirMesa(List<Humano> jogadores, Dealer dealer, boolean ocultarCartaDealer) {
        System.out.println("\n==================================================================");
        System.out.println("                      🎰 MESA DE JOGO 🎰                         ");
        System.out.println("==================================================================");

        // Exibe o Dealer
        System.out.print(" 🎩 DEALER: ");
        if (ocultarCartaDealer) {
            System.out.println("[Primeira carta visível, 2ª oculta]");
        } else {
            System.out.println("Total: " + dealer.getPontuacao() + " pontos");
        }
        System.out.println(CartaASCIIFormatter.formatarMaoASCII(dealer.getMao().getCartas(), ocultarCartaDealer));
        System.out.println();

        // Exibe cada Jogador Humano
        for (Humano j : jogadores) {
            String statusExtra = j.isEstourou() ? " ❌ (ESTOUROU!)" : (j.isBlackjack() ? " 🌟 (BLACKJACK!)" : "");
            System.out.printf(" 👤 %s (Saldo: R$ %.2f | Total: %d pts)%s\n",
                    j.getNome().toUpperCase(), j.getSaldoFichas(), j.getPontuacao(), statusExtra);
            System.out.println(CartaASCIIFormatter.formatarMaoASCII(j.getMao().getCartas(), false));
            System.out.println();
        }
        System.out.println("==================================================================");
    }

    public char solicitarAcaoJogador(Humano jogador) {
        while (true) {
            System.out.printf("\nTurno de %s (Total: %d pts) | [1] Pedir Carta (Hit) | [2] Parar (Stand) | Opção: ",
                    jogador.getNome(), jogador.getPontuacao());
            String entrada = scanner.nextLine().trim();
            if (entrada.equals("1") || entrada.equalsIgnoreCase("h")) {
                return '1';
            } else if (entrada.equals("2") || entrada.equalsIgnoreCase("s")) {
                return '2';
            }
            System.out.println("❌ Opção inválida. Escolha 1 para Pedir Carta ou 2 para Parar.");
        }
    }

    public void exibirResultadosRodada(List<ResultadoJogador> resultados) {
        System.out.println("\n==================================================================");
        System.out.println("                📢 RESULTADO DA RODADA                            ");
        System.out.println("==================================================================");
        for (ResultadoJogador res : resultados) {
            System.out.printf(" 👤 %-15s : %-35s (Saldo: R$ %.2f)\n",
                    res.jogador().getNome(),
                    res.resultado().getMensagem(),
                    res.jogador().getSaldoFichas());
        }
        System.out.println("==================================================================\n");
    }

    public void fechar() {
        scanner.close();
    }
}
