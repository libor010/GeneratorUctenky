package Test;

import Main.Tisk;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TiskTest {

    // Promenne pro zachyceni konzoloveho vystupu
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Před kazdym testem presmerujeme System.out do naseho proudu
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Po testu vratime System.out do puvodniho stavu
        System.setOut(originalOut);
    }

    @Test
    void vytiskni() {
        // Priprava
        Tisk tisk = new Tisk();
        String uctenka = "Espresso - 55 Kč\nVoda - 30 Kč\nCelkem: 85 Kč\n";

        // Tisknuti uctenky
        tisk.vytiskni(uctenka);

        // Overeni jestli se vysledky rovnaji
        String ocekavanyVystup = "\n\n--- TISK ÚČTENKY ZAHÁJEN ---\n"
                + uctenka
                + "--- TISK ÚČTENKY DOKONČEN ---\n\n";

        assertEquals(ocekavanyVystup, outContent.toString());
    }
}