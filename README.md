Generátor účtenek (v1.0.0)
--------------------------
Konzolová aplikace v jazyce Java pro interaktivní zadávání nákupu, zpracování položek, načítání konfigurace obchodu a generování formátovaných pokladních účtenek.

----------------------------------------------------------------------------------------
Autoři:

Sladek,
Adamec,
Moravek,
Kratky

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

Jak sestavit a vytvořit další verzi
-----------------------------------

Varianta A: Pomocí Mavenu (Doporučeno)
--------------------------------------

  Pokud máte projekt nastavený jako Maven projekt s konfiguračním souborem pom.xml:

  Čisté sestavení a zabalení do JAR souboru:

  mvn clean package
  
  -----------------

  Spuštění aplikace:
    Výsledný spustitelný balíček se vytvoří ve složce target/. Aplikaci spustíte příkazem:

    java -jar target/generator-uctenek-1.0.0.jar


Varianta B: Ruční postup (bez Mavenu)
-------------------------------------

  Pokud kompilujete a spouštíte projekt ručně přes příkazový řádek (CLI):

  Vytvoření výstupní složky pro přeložené třídy (bin):

  mkdir bin


  Kompilace zdrojových kódů:
  --------------------------
    Přejděte do kořenové složky zdrojových kódů (např. src) a zkompilujte soubory:

    javac -d ../bin Main/*.java


  Kopírování konfiguračního souboru:
  ----------------------------------
    Zajistěte, aby byl soubor obchod.properties dostupný ve složce zdrojů / classpath (např. v bin/Main/resources/obchod.properties podle toho, jak ho načítá getClassLoader().getResourceAsStream()).

  Spuštění aplikace:
  ------------------

    java -cp bin Main.Main
