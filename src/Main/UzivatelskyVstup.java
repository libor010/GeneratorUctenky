package Main;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UzivatelskyVstup {

    private static final String KONEC = "konec";
    private static final int MAX_DELKA_NAZVU = 100;
    private static final int MAX_POLOZEK = 100;
    private static final BigDecimal MAX_CENA = new BigDecimal("1000000");

    // Povolené znaky v názvu: písmena (i s diakritikou), číslice, mezera a základní interpunkce.
    // Středník tu záměrně chybí, protože se používá jako oddělovač "Název;Cena".
    private static final Pattern PLATNY_NAZEV = Pattern.compile("^[\\p{L}\\p{N} .,'()/%-]+$");

    // Název musí obsahovat alespoň jedno písmeno (i s diakritikou)
    private static final Pattern OBSAHUJE_PISMENO = Pattern.compile(".*\\p{L}.*");

    // Cena: 1-7 číslic, volitelně desetinná tečka a 1-2 desetinná místa (čárka se předtím převede na tečku)
    private static final Pattern PLATNA_CENA = Pattern.compile("^\\d{1,7}(\\.\\d{1,2})?$");

    public List<String> nactiPolozky() {
        Scanner scanner = new Scanner(System.in);
        List<String> kosik = new ArrayList<>();

        System.out.println("Zadávejte položky nákupu. Pro ukončení zadejte slovo 'konec'.");

        while (kosik.size() < MAX_POLOZEK) {
            String nazev = nactiNazev(scanner);
            if (nazev == null) {
                break; // uživatel zadal 'konec' nebo skončil vstup
            }

            BigDecimal cena = nactiCenu(scanner);
            if (cena == null) {
                break; // vstup skončil uprostřed zadávání
            }

            // Data ukládáme ve formátu "Název;Cena" pro snadné předání do další třídy
            kosik.add(nazev + ";" + cena.toPlainString());
        }

        if (kosik.size() >= MAX_POLOZEK) {
            System.out.println("Dosažen maximální počet položek (" + MAX_POLOZEK + ").");
        }
        return kosik;
    }

    /** Vrací platný název, nebo null při 'konec' / konci vstupu. Při chybě se ptá znovu. */
    private String nactiNazev(Scanner scanner) {
        while (true) {
            System.out.print("Název položky (nebo 'konec'): ");
            if (!scanner.hasNextLine()) {
                return null;
            }

            String nazev = normalizuj(scanner.nextLine());

            if (nazev.equalsIgnoreCase(KONEC)) {
                return null;
            }

            String chyba = zkontrolujNazev(nazev);
            if (chyba == null) {
                return nazev;
            }
            System.out.println("Neplatný název: " + chyba);
        }
    }

    /** Vrací platnou cenu, nebo null při konci vstupu. Při chybě se ptá znovu. */
    private BigDecimal nactiCenu(Scanner scanner) {
        while (true) {
            System.out.print("Cena položky v Kč: ");
            if (!scanner.hasNextLine()) {
                return null;
            }

            String vstup = normalizuj(scanner.nextLine()).replace(',', '.');

            if (!PLATNA_CENA.matcher(vstup).matches()) {
                System.out.println("Neplatná cena. Zadejte kladné číslo, např. 49,90.");
                continue;
            }

            BigDecimal cena = new BigDecimal(vstup);
            if (cena.signum() <= 0 || cena.compareTo(MAX_CENA) > 0) {
                System.out.println("Cena musí být větší než 0 a nejvýše " + MAX_CENA.toPlainString() + " Kč.");
                continue;
            }
            return cena.setScale(2);
        }
    }

    /** Sjednotí Unicode (NFKC), ořízne okraje a sloučí opakované mezery. */
    private String normalizuj(String vstup) {
        String s = Normalizer.normalize(vstup, Normalizer.Form.NFKC);
        return s.trim().replaceAll("\\s+", " ");
    }

    /** Vrací text chyby, nebo null, pokud je název v pořádku. */
    private String zkontrolujNazev(String nazev) {
        if (nazev.isEmpty()) {
            return "název nesmí být prázdný.";
        }
        if (nazev.length() > MAX_DELKA_NAZVU) {
            return "název může mít nejvýše " + MAX_DELKA_NAZVU + " znaků.";
        }
        if (!PLATNY_NAZEV.matcher(nazev).matches()) {
            return "název obsahuje nepovolené znaky.";
        }
        if (!OBSAHUJE_PISMENO.matcher(nazev).matches()) {
            return "název musí obsahovat alespoň jedno písmeno.";
        }
        return null;
    }
}