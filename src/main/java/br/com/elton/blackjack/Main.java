package br.com.elton.blackjack;

import br.com.elton.blackjack.model.Carta;
import br.com.elton.blackjack.model.Humano;
import br.com.elton.blackjack.service.JogoService;
import br.com.elton.blackjack.service.JogoService.ResultadoJogador;
import br.com.elton.blackjack.ui.ConsoleUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Ponto de entrada da aplicação Blackjack (21) em modo Multiplayer (2 Jogadores).
 */
public class Main {

    private static final double SALDO_INICIAL = 100.0;
    private static final int NUM_JOGADORES = 2;

    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI();
        ui.exibirCabecalho();

        List<String> nomes = ui.solicitarNomesJogadores(NUM_JOGADORES);
        JogoService jogoService = new JogoService(nomes, SALDO_INICIAL);

        boolean continuarJogando = true;

        while (continuarJogando) {
            // Verifica se pelo menos um jogador tem saldo de fichas > 0
            List<Humano> jogadoresComSaldo = jogoService.getJogadores().stream()
                    .filter(j -> j.getSaldoFichas() > 0)
                    .toList();

            if (jogadoresComSaldo.isEmpty()) {
                System.out.println("💸 O saldo de fichas de todos os jogadores acabou! Fim de jogo.");
                break;
            }

            List<Double> apostas = new ArrayList<>();
            boolean algumApostou = false;

            for (Humano j : jogoService.getJogadores()) {
                if (j.getSaldoFichas() > 0) {
                    double aposta = ui.solicitarValorAposta(j);
                    apostas.add(aposta);
                    if (aposta > 0) {
                        algumApostou = true;
                    }
                } else {
                    apostas.add(0.0);
                    System.out.println("⚠️ " + j.getNome() + " está sem fichas e não participa desta rodada.");
                }
            }

            if (!algumApostou) {
                System.out.println("\n👋 Nenhum jogador realizou apostas. Encerrando a sessão. Até a próxima!");
                break;
            }

            if (!jogoService.iniciarNovaRodada(apostas)) {
                System.out.println("❌ Falha ao iniciar a rodada com as apostas informadas.");
                continue;
            }

            // Exibe a mesa inicial com a carta do Dealer oculta
            ui.exibirMesa(jogoService.getJogadores(), jogoService.getDealer(), true);

            // Turno individual de cada jogador humano
            for (Humano jogador : jogoService.getJogadores()) {
                if (jogador.getApostaAtual() <= 0) continue;

                if (jogador.isBlackjack()) {
                    System.out.println("\n🌟 INCRÍVEL! " + jogador.getNome() + " conseguiu um Blackjack Natural!");
                    continue;
                }

                System.out.println("\n--- Turno de " + jogador.getNome() + " ---");
                while (!jogador.isEstourou()) {
                    char acao = ui.solicitarAcaoJogador(jogador);
                    if (acao == '1') {
                        Carta cartaComprada = jogoService.pedirCartaJogador(jogador);
                        System.out.println("🎴 " + jogador.getNome() + " comprou a carta: " + cartaComprada);
                        ui.exibirMesa(jogoService.getJogadores(), jogoService.getDealer(), true);

                        if (jogador.isEstourou()) {
                            System.out.println("❌ " + jogador.getNome() + " estourou 21 pontos!");
                            break;
                        }
                    } else if (acao == '2') {
                        System.out.println("✋ " + jogador.getNome() + " decidiu Parar (Stand).");
                        break;
                    }
                }
            }

            // Turno do Dealer (se pelo menos um jogador não estourou)
            if (jogoService.algumJogadorVivo()) {
                System.out.println("\n🎩 Executando o turno do Dealer...");
                jogoService.executarTurnoDealer();
            }

            // Exibe a mesa final revelando todas as cartas
            ui.exibirMesa(jogoService.getJogadores(), jogoService.getDealer(), false);

            // Avalia e exibe os resultados individuais da rodada
            List<ResultadoJogador> resultados = jogoService.avaliarResultados();
            ui.exibirResultadosRodada(resultados);
        }

        ui.fechar();
    }
}
