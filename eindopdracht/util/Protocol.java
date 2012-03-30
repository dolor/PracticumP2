package eindopdracht.util;

// Versie 2



public interface Protocol {
		/**
		 * Met de join commando maak je contact met de server. Hierbij geef je je
		 * naam en het gewenste aantal spelers in het potje dat je wilt spelen aan.
		 * Argumenten: <naam> <aantalSpelers>
		 */
		
		public static final String JOIN = "join";
		/**
		 * Wanneer een speller begint aan zijn zet mag hij eerst een knikker op een
		 * tile plaatsen. Welke hij kiest wordt aangegeven door de letter van het
		 * blok waar deze tile in zit, en dan door het nummer van de tile in deze
		 * blok.
		 * Argumenten:<letter> <nummer>
		 *
		 */
		public static final String SET_TILE = "set_tile";
		/**
		 * Als tweede deel van zijn zet moet de speller een blok een kwartslag
		 * draaien. Dit geeft hij aan met de letter van het blok dat hij wil
		 * draaien, en de richting waarin deze gedraaid moet worden, clockwise of
		 * counter-clockwise.
		 * Argumenten: <letter> <cw/ccw>
		 * 
		 * Wanneer een speler een blok wil draaien, stuurt hij daarna of hij hem clockwise of counter-clockwise wil draaien.
		 */		
		public static final String TURN_BLOCK = "turn_block";
		public static final String CW = "cw";
		public static final String CCW = "ccw";
		/**
		 * Wanneer de speler een spel voortijdig wil beeindigen. Hierbij wordt de volledige verbinding met de server verbroken.
		 * 
		 */
		public static final String QUIT = "quit";

		/**
		 * Wanneer een bericht door een client naar de server gestuurd wordt, stuurt
		 * deze dit bericht door naar alle mensen in de chatroom, en de naam van de
		 * persoon die het bericht gestuurd heeft.
		 * Argumenten: <message> 
		 */
		public static final String CHAT = "chat";
		/**
		 * Chanllenge-functie
		 * 
		 * Als een speler andere spelers wil uitdagen, stuurt hij dit commando met 1
		 * tot 3 namen, afhankelijk van hoeveel mensen hij wil uitdagen. Zodra een
		 * speler een challenge verstuurt, of ontvangt, wordt hij uit de
		 * challenge-lobby gehaald. Pas als niemand een uitdaging aanneemt, of
		 * respectievelijk de ontvangen uitdaging afgewezen wordt, komt de speler
		 * terug in de lobby.
		 * Argumenten: <naam1> <naam2> <naam3>
		 */
		public static final String CHALLENGE = "challenge";
		/**
		 * Wanneer een speler uitgedaagd is, kan hij een reply terug sturen. Hierin
		 * staat op wiens challenge hij reageert, en of hij de challenge accepteert.
		 * Argumenten: <naam> <Boolean>
		 */
		public static final String ACCEPT = "accept";
		
		/**
		 * ServerToClient
		 * 
		 * Zodra je de server gejoined bent, stuurt deze een commando terug met
		 * daarin je naam, en mogelijk gevolgd door een getal, als er al andere
		 * mensen deze naam gebruiken. Dit getal is oplopend.
		 * Argumenten: <naam>
		 */
		public static final String CONNECTED = "connected";
		/**
		 * Als je niet direct kunt beginnen met een potje, kom je in een lobby
		 * terecht, en stuurt de server een bericht met alle andere wachtende
		 * mensen.
		 * Argumenten: <naam1> <naam2> <ect>
		 */
		public static final String PLAYERS = "players";
		/**
		 * Zodra een potje kan beginnen stuurt de server een bericht om aan te geven
		 * dat het potje begint, met daarin de volgorde van de spelers. Dit bericht
		 * bevat zoveel spelers als dat er aan een potje meedoen.
		 * Argumenten: <naam1> <naam2> <naam3> <naam4>
		 */
		public static final String START = "start";
		/**
		 * Zodra het de beurt van een speler is, stuurt de server deze speler een
		 * bericht hierover.
		 */
		public static final String YOUR_TURN = "your_turn";
		/**
		 * Zodra een speler een zet doet wordt deze zet en de speler die hem doet
		 * aan alle spellers doorgestuurd.
		 * Agrumenten: <letter> <nummer> <naam> 
		 * Argumenten: <letter> <naam>
		 * 
		 * Wanneer een speler een blok wil draaien, stuurt hij daarna of hij hem clockwise of counter-clockwise wil draaien.
		 *
		 */
		public static final String BROADCAST_SET_TILE = "set_tile";
		public static final String BROADCAST_TURN_BLOCK = "turn_block";
		/**
		 * Wanneer een potje afgelopen is, stuurt de server een bericht met daarin 
		 * de reden voor het beeindigen van het potje en de verantwoordelijke persoon. 
		 * De code kan de volgende dingen aangeven:
		 *	1: Winnaar		Naam geeft winnaar of winnaars, als het 2 spelers tegelijk winnen, aan.
		 *	2: Remise		Geen naam
		 *	3: Cheat		Naam van de valsspeler. Dit is ook van toepassing bij een incorrecte zet.
		 *	4: Diconnect		Naam van de gedisconnecte persoon
		 * Argumenten: <code> <naam>
		 */
		public static final String END_GAME = "end_game";
		
		/**
		 * Wanneer de server de verbinding wil verbreken. Dit commando wordt
		 * automatisch gestuurd zodra een potje eindigt.
		 * 
		 * LET EROP DAT ER TWEE KEER QUIT IN DEZE INTERFACE STAAT!
		 */
		public static final String QUIT_SERVER = "quit";
		
		/**
		 * Chat-functie
		 * 
		 * Wanneer een bericht door een client naar de server gestuurd wordt, stuurt
		 * deze dit bericht door naar alle mensen in de chatroom, en de naam van de
		 * persoon die het bericht gestuurd heeft. Hierbij wordt de volledige verbinding met de server verbroken.
		 * 
		 * Argumenten: <naam> <message>
		 * 
		 * LET EROP DAT ER TWEE KEER CHAT IN DEZE INTERFACE STAAT!
		 */
		public static final String CHAT_SERVER = "chat";
		/**
		 * Challenge-functie
		 * 
		 * Wanneer een client wil verbinden met een server die geen challenge-mode
		 * ondersteund, moet de server hem hier een bericht over sturen, gevolgd
		 * door een quit-command.
		 */
		public static final String NO_CHALLENGE = "no_challenge";
		/**
		 * Wanneer een speler een challengecommando stuurt naar de server, stuurt de
		 * server een commando naar elk van de genoemde spellers, met als argument
		 * de speler die aan het uitdagen is.
		 * 
		 * Argumenten: <naam>
		 * 
		 * LET EROP DAT ER TWEE KEER CHALLENGE IN DEZE INTERFACE STAAT!
		 */
		public static final String CHALLENGE_SERVER = "challenge";
		/**
		 * Wanneer een uitdaging complete afgewezen wordt, ontvangt de uitdager hier
		 * bericht van. Als maar een deel van de spelers accepteert, wordt het potje
		 * alsnog met deze spelers gestart.
		 */
		public static final String CHALLENGE_FAILED = "challenge_failed";
	}

