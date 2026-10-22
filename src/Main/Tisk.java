package Main;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Tisk {

    public void vytiskni(String textUctenky) {
        LocalDateTime aktualniCas = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String timestamp = "Čas tisku: " + aktualniCas.format(formatter) + "\n";


        /*  Toto je supr trida pro ukladani uctenky
         *   BufferedWriter byl pouzit pro jeho ucinne schopnosti zapisovani
         *   Uctenka je ulozena v souboru uctenka.txt
         */

        // Inicializace objektu BufferedWriter pro zapisovani do souboru
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("uctenka.txt"))){


            String tiskZahajen = "\n\n--- TISK ÚČTENKY ZAHÁJEN ---";
            System.out.println(tiskZahajen.trim());
            bw.append(tiskZahajen);

            // Prace s uctenkou
            System.out.print(textUctenky.trim());
            bw.append(textUctenky);


            String tiskUkoncen = "--- TISK ÚČTENKY DOKONČEN ---\n";
            System.out.println(tiskUkoncen.trim());
            bw.append(tiskUkoncen);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}