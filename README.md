# Šachy
Program byl vytvořen v rámci předmětů Programování v Javě a Vývoj klientských aplikací v JavaScriptu. Jedná se o online šachy. Hrát lze jak proti počítači tak i s kamarádem přes internet.

Veškerá logika šachů je implementována na serveru v Javě, na klientovi se pak vykresluje šachovnice pomocí JavaScriptu. Šachový server dokáže současně obsluhovat i více paralelních her.

# Popis uživatelského rozhraní

Na domovské stránce si můžete zvolit, zda chcete hrát s počítačem či s kamarádem online. V případě, že vyberete možnost "s kamarádem", bude vám vygenerován link, který musíte poslat soupeři. V momentě, kdy dotyčný dorazí na danou URL, hra začíná. S figurkami lze hýbat dvěma způsoby. Jedním z nich je klasické přetáhnutí figurky na danou pozici, dalším je vybrání tahu z nabídky.

# Komunikace klient-server

Jednotliví klienti komunikují se serverem v reálném čase pomocí websoketu. Skrze něj si posílají JSON požadavky. Jak přesně komunikace vypadá můžeme vidět na tomto diagramu.

![API_Diagram](uploads/2bc5885a1968f4ab4375ffb496223f38/API_Diagram.png)

Nejprve hráč odešle požadavek na server "NewGameRequest". Server vygeneruje URL a odešle jí klientovi zpátky v "NewGameResponce". Jakmile protihráč na daný link dorazí, pošle na server "ConnectRequest". V ten moment odešle server "StartGameRequest" obou hráčům. V něm budou informace o barvě daného hráče, která je vybrána náhodně, a počáteční pozice jednotlivých figurek. Pokaždé když nějaký z hráčů odehraje, klient odešle informaci o tahu na server v "MoveRequest". Server odpoví v "MoveResponce", zda je tah validní a pokud ano, odešle "OponnentMovedRequest" soupeři. Tomu se tah zobrazí na obrazovku. Jakmile někdo z hráčů dá soupeři šach-mat, server na to upozorní klienty požadavkem "GameOverRequest". Klienti odpoví "GameOverResponce" na znamení, že byl přijmut. Komunikace končí.

Jednotlivé požadavky serveru jsou do JSON formátu dynamicky vytvářeny z datových objektů uložených v balíčku chess.comunication.dto pomocí knihovny GSON. Konverze funguje také naopak. Pokud přijde request od klienta, JSON string je převeden na objekt.

# Architektura

## Backend

V aplikaci jsem vytvořil třídy jednotlivých figurek. Jejich společný předek je třída Piece. Figurky uchovávají informace o svoji poloze a vlastnostech. Vlastností figurky myslím například směr, kterým se může pohybovat. Ten je definován třídou vektor. Zde jsem se inspiroval lineární algebrou. Každá figura tedy obsahuje list vektorů Například věž se může pohybovat horizontálně a vertikálně, tudíž bude obsahovat vektory (1, 0) a (0, 1). Střelec bude mít vektory (1, 1) a (-1, 1). Z těchto vektorů pak lze vytvořit lineární kombinace a získat tím možné pozice, kam může hráč táhnout. V tomto smyslu počítám lineární kombinace na každém vektoru figurky zvlášť a množiny pozic na závěr sjednotím. Nemohu dělat lineární kombinace celého sezamu vektorů jako celku, vyšly by mi nesmyslné výsledky.

Každá hra je reprezentována instancí třídy game. Hra má dva hráče a rozhodčího. Ten rozhoduje, zda je daný tah možný nebo ne. Činí tak na základě pravidel. Rozhodčí také dokáže hráčovi říct, kam může s danou figurkou táhnout. Vygeneruje množinu všech povolených tahů. Každé pravidlo implementuje rozhraní Rule. Rozhodčí pak ve své metodě isMovePremitted prochází postupně jednotlivá pravidla a zjišťuje, zda by daným tahem některé z nich nebylo porušeno.

Správu právě odehrávajících se her má na starosti GamesManager. Obsahuje Mapu her respektive jejich kontrolerů. Každá hra má své ID, pod kterém je v mapě uložen její kontroler. 

![chess-diagram](uploads/13a04dc8c8f3ffe647911495053d02c9/chess-diagram.png)

## Frontend
### Vykreslování

K vykreslení šachovnice používám HTML element canvas. JavaScriptem postupně vykresluji bílé a zelené čtverečky. Šířku jednoho čtverečku vypočítám z šířky canvasu, stačí ji vydělit osmi. Načtu si obrázky jednotlivých figurek a vykreslím je na šachovnici.

### Animace
Nastavil jsem posluchače na události "mousedown", "mousemove" a "mouseup". Po Kliknutí na canvas se zavolá funkce update ta překreslí šachovnici, figurky a volá sama sebe, dokud uživatel drzí myš. Umožňuje animaci přesunu figurky po šachovnici. 

![Screenshot_6](uploads/cee915c663f821757d82a42284cf0103/Screenshot_6.png)

## Technologie
Jak již už bylo řečeno, na pozadí běží Java a na frontendu JavaScript. K serializaci Java objektů do JSON souborů používám knihovnu GSON. Dále využívám java-websocket pro komunikaci serveru s klientem.
