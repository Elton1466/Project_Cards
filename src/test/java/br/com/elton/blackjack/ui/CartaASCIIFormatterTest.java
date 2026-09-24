package br.com.elton.blackjack.ui;

import br.com.elton.blackjack.model.Carta;
import br.com.elton.blackjack.model.Naipe;
import br.com.elton.blackjack.model.ValorCarta;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartaASCIIFormatterTest {

    @Test
    @DisplayName("Deve gerar ASCII Art com moldura correta para cartas")
    public void testFormatarMaoASCII() {
        List<Carta> cartas = List.of(
                new Carta(Naipe.ESPADAS, ValorCarta.AS),
                new Carta(Naipe.OUROS, ValorCarta.DEZ)
        );

        String ascii = CartaASCIIFormatter.formatarMaoASCII(cartas, false);
        assertNotNull(ascii);
        assertTrue(ascii.contains("┌───────┐"));
        assertTrue(ascii.contains("│ A     │"));
        assertTrue(ascii.contains("│   ♠   │"));
        assertTrue(ascii.contains("│ 10    │"));
        assertTrue(ascii.contains("│   ♦   │"));
        assertTrue(ascii.contains("└───────┘"));
    }

    @Test
    @DisplayName("Deve renderizar carta oculta corretamente")
    public void testFormatarMaoASCIIOculta() {
        List<Carta> cartas = List.of(
                new Carta(Naipe.COPAS, ValorCarta.REI),
                new Carta(Naipe.PAUS, ValorCarta.CINCO)
        );

        String ascii = CartaASCIIFormatter.formatarMaoASCII(cartas, true);
        assertNotNull(ascii);
        assertTrue(ascii.contains("│ ?   ? │"));
        assertTrue(ascii.contains("│   ?   │"));
    }
}
