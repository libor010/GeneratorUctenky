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

            bw.write(timestamp);

            String tiskZahajen = "\n\n--- TISK ÚČTENKY ZAHÁJEN ---";
            System.out.println(tiskZahajen.trim());
            bw.write(tiskZahajen);

            // Prace s uctenkou
            System.out.print(textUctenky.trim());
            bw.write(textUctenky);


            String tiskUkoncen = "--- TISK ÚČTENKY DOKONČEN ---\n";
            System.out.println(tiskUkoncen.trim());
            bw.write(tiskUkoncen);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}