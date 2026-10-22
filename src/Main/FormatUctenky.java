package Main;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class FormatUctenky {
    private String nazevObchodu = "Výchozí Obchod";
    private String adresaObchodu = "Neznámá adresa";
    private String ico = "00000000";

    public void nactiKonfiguraci() {
        Properties prop = new Properties();
        // Načtení souboru obchod.properties ze složky src/main/resources
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("obchod.properties")) {
            if (input != null) {
                prop.load(input);
                // Pokud v souboru klíč chybí, použijí se tyto záložní hodnoty
                nazevObchodu = prop.getProperty("nazev", "Bufet Jižní Město");
                adresaObchodu = prop.getProperty("adresa", "Ekonomická 957, Praha");
                ico = prop.getProperty("ico", "61384399");
            } else {
                System.out.println("Upozornění: Soubor obchod.properties nenalezen.");
            }
        } catch (Exception e) {
            System.out.println("Chyba při načítání souboru: " + e.getMessage());
        }
    }

    public String sestavUctenku(List<String> nakupniKosik) {
        StringBuilder sb = new StringBuilder();

        // Hlavička účtenky (zarovnáno doleva)
        sb.append(String.format("%-30s%n", nazevObchodu));
        sb.append(String.format("%-30s%n", adresaObchodu));
        sb.append(String.format("IČO: %-25s%n", ico));
        sb.append("==============================\n");

        double celkem = 0.0;

        // Výpis položek
        for (String polozka : nakupniKosik) {
            String[] data = polozka.split(";");
            if (data.length == 2) {
                String nazev = data[0];
                try {
                    // Nahrazení případné čárky za tečku, aby to Double pobral
                    double cena = Double.parseDouble(data[1].replace(",", "."));
                    celkem += cena;

                    // %-20s = text zarovnaný doleva na 20 znaků
                    // %9.2f = číslo zarovnané doprava na 9 znaků s 2 desetinnými místy
                    sb.append(String.format("%-20s %9.2f%n", nazev, cena));
                } catch (NumberFormatException e) {
                    sb.append(String.format("%-20s %9s%n", nazev, "CHYBA"));
                }
            }
        }

        // Patička účtenky
        sb.append("------------------------------\n");
        sb.append(String.format("%-20s %9.2f%n", "CELKEM K ÚHRADĚ:", celkem));
        sb.append("==============================\n");
        sb.append(String.format("%30s%n", "Děkujeme za Váš nákup!")); // zarovnáno doprava

        return sb.toString();
    }
}
