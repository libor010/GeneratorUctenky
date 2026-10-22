package Main;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class FormatUctenky {
    private String nazevObchodu = "Výchozí Obchod";
    private String adresaObchodu = "Neznámá adresa";
    private String ico = "00000000";
    private String dic = "CZ00000000";
    private String pokladni = "Neznámý";
    private String textCelkem = "CELKEM K ÚHRADĚ:";
    private String dekovnyText = "Děkujeme za Váš nákup!";
    private String oddelovacHlavni = "==============================";
    private String oddelovacVedlejsi = "------------------------------";

    // Fixní šířka účtenky podle délky oddělovače (30 znaků)
    private final int SIRKA_UCTENKY = 30;

    // Pomocná metoda pro zarovnání na střed
    private String vycentruj(String text) {
        if (text == null || text.length() >= SIRKA_UCTENKY) {
            return text;
        }
        int pocetMezerVlevo = (SIRKA_UCTENKY - text.length()) / 2;
        return " ".repeat(pocetMezerVlevo) + text;
    }

    public void nactiKonfiguraci() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("Main/resources/obchod.properties")) {
            if (input != null) {
                prop.load(input);
                nazevObchodu = prop.getProperty("nazev", nazevObchodu);
                adresaObchodu = prop.getProperty("adresa", adresaObchodu);
                ico = prop.getProperty("ico", ico);
                dic = prop.getProperty("dic", dic);
                pokladni = prop.getProperty("pokladni", pokladni);
                textCelkem = prop.getProperty("textCelkem", textCelkem);
                dekovnyText = prop.getProperty("dekovnyText", dekovnyText);
                oddelovacHlavni = prop.getProperty("oddelovacHlavni", oddelovacHlavni);
                oddelovacVedlejsi = prop.getProperty("oddelovacVedlejsi", oddelovacVedlejsi);
            } else {
                System.out.println("Upozornění: Soubor obchod.properties nenalezen. Používají se výchozí hodnoty.");
            }
        } catch (Exception e) {
            System.out.println("Chyba při načítání souboru: " + e.getMessage());
        }
    }

    public String sestavUctenku(List<String> nakupniKosik) {
        StringBuilder sb = new StringBuilder();

        // Hlavička účtenky (vycentrovaná)
        sb.append(vycentruj(nazevObchodu)).append("\n");
        sb.append(vycentruj(adresaObchodu)).append("\n");
        sb.append(vycentruj("IČO: " + ico)).append("\n");
        sb.append(vycentruj("DIČ: " + dic)).append("\n");
        sb.append(vycentruj("Obsluha: " + pokladni)).append("\n");
        sb.append(oddelovacHlavni).append("\n");

        double celkem = 0.0;

        // Výpis dynamických položek od uživatele (název doleva, cena doprava)
        for (String polozka : nakupniKosik) {
            String[] data = polozka.split(";");
            if (data.length == 2) {
                String nazev = data[0];
                try {
                    double cena = Double.parseDouble(data[1].replace(",", "."));
                    celkem += cena;

                    // %-20s zarovná text doleva, %9.2f zarovná číslo doprava
                    sb.append(String.format("%-20s %9.2f%n", nazev, cena));
                } catch (NumberFormatException e) {
                    sb.append(String.format("%-20s %9s%n", nazev, "CHYBA"));
                }
            }
        }

        // Patička účtenky (celkem zarovnané, poděkování vycentrované)
        sb.append(oddelovacVedlejsi).append("\n");
        sb.append(String.format("%-20s %9.2f%n", textCelkem, celkem));
        sb.append(oddelovacHlavni).append("\n");
        sb.append(vycentruj(dekovnyText)).append("\n");

        return sb.toString();
    }
}