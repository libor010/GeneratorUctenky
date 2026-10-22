package Test;

import Main.FormatUctenky;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormatUctenkyTest {

    private FormatUctenky formatUctenky;

    @BeforeEach
    void setUp() {
        formatUctenky = new FormatUctenky();
        // Pokusí se načíst konfiguraci (pokud soubor existuje),
        // jinak použije výchozí fallback hodnoty z kódu.
        formatUctenky.nactiKonfiguraci();
    }

    @Test
    void testSestavUctenku_StandardniKosik() {
        // Arrange (Příprava nákupního košíku - testujeme tečku i čárku v cenách)
        List<String> kosik = Arrays.asList(
                "Káva;50",
                "Dort;80,50",
                "Voda;30.0"
        );

        // Act (Sestavení účtenky)
        String vysledek = formatUctenky.sestavUctenku(kosik);

        // Assert (Ověření)
        assertNotNull(vysledek);

        // Ověříme, že obsahuje položky
        assertTrue(vysledek.contains("Káva"));
        assertTrue(vysledek.contains("Dort"));
        assertTrue(vysledek.contains("Voda"));

        // Ověříme, že správně spočítal celkovou sumu (50 + 80.5 + 30 = 160.50)
        assertTrue(vysledek.contains("CELKEM K ÚHRADĚ:"));
        assertTrue(vysledek.contains("160,50") || vysledek.contains("160.50"),
                "Celková suma by měla být 160.50 (respektující lokální desetinnou tečku/čárku)");

        // Ověříme patičku a hlavičku
        assertTrue(vysledek.contains("Děkujeme za Váš nákup!"));
        assertTrue(vysledek.contains("=============================="));
    }

    @Test
    void testSestavUctenku_ChybnaCena() {
        // Arrange (Položka s neplatnou cenou)
        List<String> kosik = Arrays.asList(
                "NeznamyProdukt;neplatnaCena"
        );

        // Act
        String vysledek = formatUctenky.sestavUctenku(kosik);

        // Assert
        assertTrue(vysledek.contains("NeznamyProdukt"));
        assertTrue(vysledek.contains("CHYBA"), "U neplatné ceny by se měl vypsat text CHYBA");
        assertTrue(vysledek.contains("CELKEM K ÚHRADĚ:"));
    }

    @Test
    void testSestavUctenku_PrazdnyKosik() {
        // Arrange
        List<String> kosik = Collections.emptyList();

        // Act
        String vysledek = formatUctenky.sestavUctenku(kosik);

        // Assert
        assertNotNull(vysledek);
        assertTrue(vysledek.contains("CELKEM K ÚHRADĚ:"));
        assertTrue(vysledek.contains("0,00") || vysledek.contains("0.00"),
                "U prázdného košíku by měla být celková částka 0");
    }
}