package br.com.elton.blackjack.service;

import br.com.elton.blackjack.model.Baralho;
import br.com.elton.blackjack.model.Carta;
import br.com.elton.blackjack.model.Dealer;
import br.com.elton.blackjack.model.Humano;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serviço orquestrador das regras do jogo Blackjack para múltiplos jogadores (2 Jogadores vs Dealer).
 */
public class JogoService {

    public enum ResultadoRodada {
        VITORIA_BLACKJACK_JOGADOR("Blackjack! Ganhou 2.5x a aposta!"),
        VITORIA_JOGADOR("Venceu a rodada!"),
        VITORIA_DEALER("Dealer venceu."),
        JOGADOR_ESTOUROU("Estourou 21 pontos!"),
        DEALER_ESTOUROU("Dealer estourou! Venceu a rodada!"),
        EMPATE("Empate (Push)! Aposta devolvida.");

        private final String mensagem;

        ResultadoRodada(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    public static record ResultadoJogador(Humano jogador, ResultadoRodada resultado) {}

    private final Baralho baralho;
    private final List<Humano> jogadores;
    private final Dealer dealer;
    private boolean rodadaEmAndamento;

    public JogoService(List<String> nomesJogadores, double saldoInicial) {
        this.baralho = new Baralho();
        this.jogadores = new ArrayList<>();
        for (String nome : nomesJogadores) {
            this.jogadores.add(new Humano(nome, saldoInicial));
        }
        this.dealer = new Dealer();
        this.rodadaEmAndamento = false;
    }

    public List<Humano> getJogadores() {
        return Collections.unmodifiableList(jogadores);
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Baralho getBaralho() {
        return baralho;
    }

    public boolean isRodadaEmAndamento() {
        return rodadaEmAndamento;
    }

    /**
     * Inicia uma nova rodada realizando as apostas de cada jogador e distribuindo as cartas iniciais.
     *
     * @param apostas Lista contendo as apostas de cada jogador correspondente.
     * @return true se todas as apostas forem válidas.
     */
    public boolean iniciarNovaRodada(List<Double> apostas) {
        if (apostas.size() != jogadores.size()) {
            return false;
        }

        // Tenta realizar a aposta de cada jogador
        for (int i = 0; i < jogadores.size(); i++) {
            if (!jogadores.get(i).fazerAposta(apostas.get(i))) {
                // Reverte apostas anteriores em caso de falha
                for (int j = 0; j < i; j++) {
                    jogadores.get(j).devolverAposta();
                }
                return false;
            }
        }

        // Limpa as mãos
        for (Humano j : jogadores) {
            j.limparMao();
        }
        dealer.limparMao();

        // Distribuição inicial (2 cartas para cada jogador humano e 2 para o Dealer)
        for (int c = 0; c < 2; c++) {
            for (Humano j : jogadores) {
                j.receberCarta(baralho.comprarCarta());
            }
            dealer.receberCarta(baralho.comprarCarta());
        }

        rodadaEmAndamento = true;
        return true;
    }

    /**
     * Um jogador específico pede uma nova carta (Hit).
     */
    public Carta pedirCartaJogador(Humano jogador) {
        if (!rodadaEmAndamento || jogador.isEstourou()) {
            return null;
        }

        Carta carta = baralho.comprarCarta();
        jogador.receberCarta(carta);
        return carta;
    }

    /**
     * Verifica se pelo menos um jogador humano não estourou 21.
     */
    public boolean algumJogadorVivo() {
        for (Humano j : jogadores) {
            if (!j.isEstourou()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executa a jogada automática do Dealer se ao menos um jogador ainda estiver na rodada.
     */
    public void executarTurnoDealer() {
        if (algumJogadorVivo()) {
            while (dealer.devePedirCarta()) {
                dealer.receberCarta(baralho.comprarCarta());
            }
        }
        rodadaEmAndamento = false;
    }

    /**
     * Avalia individualmente os resultados de cada jogador contra o Dealer.
     *
     * @return Lista com o resultado de cada jogador.
     */
    public List<ResultadoJogador> avaliarResultados() {
        rodadaEmAndamento = false;
        List<ResultadoJogador> resultados = new ArrayList<>();
        int pontosDealer = dealer.getPontuacao();

        for (Humano jogador : jogadores) {
            int pontosJogador = jogador.getPontuacao();
            ResultadoRodada res;

            if (jogador.isEstourou()) {
                jogador.perderAposta();
                res = ResultadoRodada.JOGADOR_ESTOUROU;
            } else if (jogador.isBlackjack() && !dealer.isBlackjack()) {
                jogador.receberRecompensa(2.5); // Pagamento 3:2
                res = ResultadoRodada.VITORIA_BLACKJACK_JOGADOR;
            } else if (dealer.isEstourou()) {
                jogador.receberRecompensa(2.0); // Pagamento 1:1
                res = ResultadoRodada.DEALER_ESTOUROU;
            } else if (pontosJogador > pontosDealer) {
                jogador.receberRecompensa(2.0);
                res = ResultadoRodada.VITORIA_JOGADOR;
            } else if (pontosDealer > pontosJogador) {
                jogador.perderAposta();
                res = ResultadoRodada.VITORIA_DEALER;
            } else {
                jogador.devolverAposta();
                res = ResultadoRodada.EMPATE;
            }

            resultados.add(new ResultadoJogador(jogador, res));
        }

        return resultados;
    }
}
