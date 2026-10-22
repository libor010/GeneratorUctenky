package Main;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class FormatUctenky {
    // Všechny tyto hodnoty se nyní dají přepsat ze souboru
    private String nazevObchodu = "Výchozí Obchod";
    private String adresaObchodu = "Neznámá adresa";
    private String ico = "00000000";
    private String dic = "CZ00000000";
    private String pokladni = "Neznámý";
    private String textCelkem = "CELKEM K ÚHRADĚ:";
    private String dekovnyText = "Děkujeme za Váš nákup!";
    private String oddelovacHlavni = "==============================";
    private String oddelovacVedlejsi = "------------------------------";

    public void nactiKonfiguraci() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("Main/resources/obchod.properties")) {            if (input != null) {
                prop.load(input);
                // Načtení maximálního množství dat, zbytek si ponechá výchozí hodnoty výše
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

        // Hlavička účtenky kompletně z konfigurace
        sb.append(String.format("%-30s%n", nazevObchodu));
        sb.append(String.format("%-30s%n", adresaObchodu));
        sb.append(String.format("IČO: %-25s%n", ico));
        sb.append(String.format("DIČ: %-25s%n", dic));
        sb.append(String.format("Obsluha: %-21s%n", pokladni));
        sb.append(oddelovacHlavni).append("\n");

        double celkem = 0.0;

        // Výpis dynamických položek od uživatele
        for (String polozka : nakupniKosik) {
            String[] data = polozka.split(";");
            if (data.length == 2) {
                String nazev = data[0];
                try {
                    double cena = Double.parseDouble(data[1].replace(",", "."));
                    celkem += cena;
                    sb.append(String.format("%-20s %9.2f%n", nazev, cena));
                } catch (NumberFormatException e) {
                    sb.append(String.format("%-20s %9s%n", nazev, "CHYBA"));
                }
            }
        }

        // Patička účtenky kompletně z konfigurace
        sb.append(oddelovacVedlejsi).append("\n");
        sb.append(String.format("%-20s %9.2f%n", textCelkem, celkem));
        sb.append(oddelovacHlavni).append("\n");
        sb.append(String.format("%30s%n", dekovnyText));

        return sb.toString();
    }
}