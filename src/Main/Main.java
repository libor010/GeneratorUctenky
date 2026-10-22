//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
package Main;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println("=== Generátor účtenek v1.0.0 ===");

        // 1. Inicializace a načtení fixních dat obchodu ze souboru
        FormatUctenky formater = new FormatUctenky();
        formater.nactiKonfiguraci();

        // 2. Načtení dynamických dat nákupu od uživatele z terminálu
        UzivatelskyVstup vstup = new UzivatelskyVstup();
        List<String> nakupniKosik = vstup.nactiPolozky();

        // 3. Spojení dat a naformátování výsledné účtenky
        String vyslednaUctenka = formater.sestavUctenku(nakupniKosik);

        // 4. Výpis naformátované účtenky
        Tisk tiskarna = new Tisk();
        tiskarna.vytiskni(vyslednaUctenka);
    }
}
