package br.com.elton.blackjack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaoTest {

    private Mao mao;

    @BeforeEach
    public void setUp() {
        mao = new Mao();
    }

    @Test
    @DisplayName("Deve calcular corretamente pontuação sem Ás")
    public void testCalcularPontuacaoSemAs() {
        mao.adicionarCarta(new Carta(Naipe.OUROS, ValorCarta.DEZ));
        mao.adicionarCarta(new Carta(Naipe.ESPADAS, ValorCarta.SETE));
        assertEquals(17, mao.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve calcular Ás como 11 quando não estoura 21")
    public void testAsValendoOnze() {
        mao.adicionarCarta(new Carta(Naipe.COPAS, ValorCarta.AS));
        mao.adicionarCarta(new Carta(Naipe.PAUS, ValorCarta.NOVE));
        assertEquals(20, mao.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve reajustar o Ás para 1 quando a pontuação exceder 21")
    public void testAsReajustadoParaUm() {
        mao.adicionarCarta(new Carta(Naipe.COPAS, ValorCarta.AS));
        mao.adicionarCarta(new Carta(Naipe.PAUS, ValorCarta.REI));
        mao.adicionarCarta(new Carta(Naipe.OUROS, ValorCarta.CINCO));
        // Ás(11) + Rei(10) + 5 = 26 -> Reajusta Ás para 1 -> Total = 16
        assertEquals(16, mao.calcularPontuacao());
        assertFalse(mao.isEstourou());
    }

    @Test
    @DisplayName("Deve calcular pontuação de dois Áses corretamente")
    public void testDoisAses() {
        mao.adicionarCarta(new Carta(Naipe.COPAS, ValorCarta.AS));
        mao.adicionarCarta(new Carta(Naipe.ESPADAS, ValorCarta.AS));
        // Ás(11) + Ás(11) = 22 -> Reajusta um Ás para 1 -> Total = 12
        assertEquals(12, mao.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve identificar Blackjack Natural (21 pontos com 2 cartas)")
    public void testIdentificarBlackjack() {
        mao.adicionarCarta(new Carta(Naipe.ESPADAS, ValorCarta.AS));
        mao.adicionarCarta(new Carta(Naipe.COPAS, ValorCarta.VALETE));
        assertTrue(mao.isBlackjack());
        assertEquals(21, mao.calcularPontuacao());
    }

    @Test
    @DisplayName("Deve detectar se a mão estourou 21 pontos")
    public void testEstourou() {
        mao.adicionarCarta(new Carta(Naipe.ESPADAS, ValorCarta.DEZ));
        mao.adicionarCarta(new Carta(Naipe.COPAS, ValorCarta.REI));
        mao.adicionarCarta(new Carta(Naipe.OUROS, ValorCarta.CINCO));
        // 10 + 10 + 5 = 25
        assertTrue(mao.isEstourou());
    }
}
