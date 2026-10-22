package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UzivatelskyVstup {

    public List<String> nactiPolozky() {
        Scanner scanner = new Scanner(System.in);
        List<String> kosik = new ArrayList<>();

        System.out.println("Zadávejte položky nákupu. Pro ukončení zadejte slovo 'konec'.");

        while (true) {
            System.out.print("Název položky (nebo 'konec'): ");
            String nazev = scanner.nextLine();

            if (nazev.equalsIgnoreCase("konec")) {
                break;
            }

            System.out.print("Cena položky v Kč: ");
            String cena = scanner.nextLine();

            // Uložíme si data ve formátu "Název;Cena" pro snadné předání do další třídy
            kosik.add(nazev + ";" + cena);
        }

        return kosik;
    }
}
