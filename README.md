Generátor účtenek (v1.0.0)
--------------------------
Konzolová aplikace v jazyce Java pro interaktivní zadávání nákupu, zpracování položek, načítání konfigurace obchodu a generování formátovaných pokladních účtenek.

----------------------------------------------------------------------------------------
Autoři:

Sládek,
Adamec,
Morávek,
Krátky

-----------------------------------------------------------------------------------------

Struktura projektu
------------------

Aplikace se skládá z následujících hlavních tříd (v balíčku Main):

  Main – Spouštěcí třída (main), která řídí tok programu (načtení vstupu, formátování a tisk).

  UzivatelskyVstup – Zajišťuje interaktivní načítání položek od uživatele přes konzoli (Scanner) včetně validace názvů a cen.

  FormatUctenky – Stará se o načtení konfigurace ze souboru obchod.properties a sestavení textu účtenky včetně výpočtu celkové částky.

  Tisk – Zodpovídá za finální výstup účtenky do konzole.

  Konfigurační soubor se nachází v src/main/resources/obchod.properties a obsahuje údaje o obchodu (název, adresa, IČO, DIČ, obsluha, oddělovače a texty).

----------------------------------------------------------------------------------------

JAK SPUSTIT A VYTVOŘIT APLIKACI (JAR)
-------------------------------------

1. JAK VYROBIT .JAR SOUBOR (MAVEN):
- Otevři terminál ve složce s projektem.
- Napiš a odesl příkaz:
  mvn clean package
- Ve složce "target" se ti vygeneruje hotový .jar soubor.

2. JAK APLIKACI SPUSTIT:
- Otevři terminál tam, kde máš uložený hotový .jar soubor.
- Napiš příkaz:
  java -jar GeneratorUctenka.jar
- Stiskni Enter. Program se rozběhne, načte nastavení obchodu, vyzve tě k zadání položek, spočítá celkovou cenu a vytiskne účtenku.
