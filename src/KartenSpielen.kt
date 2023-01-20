//Auf gehts eine Runde BlackJack! Hier ist ein vollständiges Kartendeck.
val kartendeck: List<String> = listOf(
    "Pik Ass", "Pik Koenig", "Pik Dame", "Pik Bube", "Pik 10", "Pik 9",
    "Pik 8", "Pik 7", "Pik 6", "Pik 5", "Pik 4", "Pik 3", "Pik 2",
    "Kreuz Ass", "Kreuz Koenig", "Kreuz Dame", "Kreuz Bube", "Kreuz 10", "Kreuz 9",
    "Kreuz 8", "Kreuz 7", "Kreuz 6", "Kreuz 5", "Kreuz 4", "Kreuz 3", "Kreuz 2",
    "Herz Ass", "Herz Koenig", "Herz Dame", "Herz Bube", "Herz 10", "Herz 9",
    "Herz 8", "Herz 7", "Herz 6", "Herz 5", "Herz 4", "Herz 3", "Herz 2",
    "Karo Ass", "Karo Koenig", "Karo Dame", "Karo Bube", "Karo 10", "Karo 9",
    "Karo 8", "Karo 7", "Karo 6", "Karo 5", "Karo 4", "Karo 3", "Karo 2"
)

fun main() {
    /* Die Variable meinDeck ist euer Deck mit dem ihr arbeiten könnt.
    Mit der Funktion mischen() könnt ihr das Deck mischen.
    Mit der Funktion eineKarteZiehen() könnt ihr aus eurem Deck die oberste Karte ziehen.
    Beachtet, dass die Funktion die Karte auch aus eurem Deck entfernt.
    Mit der Funktion kartenWert() bekommst du den Wert einer Karte als Int zurück.
    Unter dem Kommentar siehst du wie du die Funktionen benutzen kannst.
     */
    var meinDeck = kartendeck.toMutableList()
    mischen(meinDeck)

    var handComputer = erzeugeHand(meinDeck, 0)
    var handSpieler = erzeugeHand(meinDeck, 1)

    if(gesamtwertHand(handSpieler) == 21){
        println("Du hast das Spiel beim ersten zug gewonnen da deine Karten einen Gesamtwert von ${gesamtwertHand(handSpieler)} haben!")
    }

    var stand = false

    while(gesamtwertHand(handSpieler) < 21 && !stand){
        stand = hitOrStand(meinDeck, handSpieler, handComputer)
    }

    if(gesamtwertHand(handSpieler) > 21){
        println("Du hast das Spiel verloren und einen Gesamtwert von ${gesamtwertHand(handSpieler)} erreicht!")
    }else if(gesamtwertHand(handSpieler) <= 21 && gesamtwertHand(handComputer) > 21){
        println("Du hast das Spiel gewonnen und einen Gesamtwert von ${gesamtwertHand(handSpieler)} erreicht!")
        println("Der Computer hat hingegen den Wert ${gesamtwertHand(handComputer)} erreicht!")
    }else if(gesamtwertHand(handSpieler) <= 21 && gesamtwertHand(handComputer) <= 21){
        if(gesamtwertHand(handSpieler) > gesamtwertHand(handComputer)){
            println("\nDu hast das Spiel gewonnen!")
            println("Du hast einen Gesamtwert von ${gesamtwertHand(handSpieler)}")
            println("Der Computer hat einen Gesamtwert von ${gesamtwertHand(handComputer)}")
        }else if(gesamtwertHand(handSpieler) < gesamtwertHand(handComputer)){
            println("\nDu hast das Spiel verloren!")
            println("Du hast einen Gesamtwert von ${gesamtwertHand(handSpieler)}")
            println("Der Computer hat einen Gesamtwert von ${gesamtwertHand(handComputer)}")
        }
    }

}

fun mischen(deck: MutableList<String>) {
    deck.shuffle()
}

fun eineKarteZiehen(deck: MutableList<String>): String {
    var karte = deck.first()
    deck.remove(deck.first())
    return karte
}

fun kartenWert(karte: String): Int {
    var kartenWert = karte.split(" ")[1]

    return when (kartenWert) {
        "2" -> 2
        "3" -> 3
        "4" -> 4
        "5" -> 5
        "6" -> 6
        "7" -> 7
        "8" -> 8
        "9" -> 9
        in listOf("10", "Koenig", "Dame", "Bube") -> 10
        "Ass" -> 11
        else -> 0
    }
}

fun gesamtwertHand(hand: List<String>) : Int{
    var gesamtWert = 0

    for(karte in hand){
        gesamtWert += kartenWert(karte)
    }

    return gesamtWert
}

fun erzeugeHand(deck: MutableList<String>, player: Int): MutableList<String>{
    //Player = 0 steht für Computer, 1 für den Spieler

    var karte1 = eineKarteZiehen(deck)
    var karte2 = eineKarteZiehen(deck)
    var hand = mutableListOf(karte1, karte2)

    mischen(deck)

    if(player == 0){
        println("Die stärkere Karte des Gegners hat einen Wert von: ${wertGroessterKarte(hand)}\n")
    }else if(player == 1){
        println("Du hast die Karte: -$karte1- und die Karte: -$karte2- gezogen!")
        println("Deine Karten haben einen Gesamtwert von ${gesamtwertHand(hand)}")
    }

    return hand
}

fun handWertUberpruefen(hand: MutableList<String>): Boolean{
    return gesamtwertHand(hand) > 21
}

fun hitOrStand(deck: MutableList<String>, handSpieler: MutableList<String>, handComputer: MutableList<String>): Boolean{
    println("\nMöchtest du eine weitere Karte ziehen (hit) ? (stand) für Nein")
    println("Eingabe: ")
    var eingabe = readln()

    if(eingabe == "hit"){
        var gezogeneKarte = eineKarteZiehen(deck)
        handSpieler.add(gezogeneKarte)
        var neuerGesamtWert: Int

        println("\nDu hast die Karte: $gezogeneKarte gezogen!")
        println("Deine Karten haben einen neuen Gesamtwert von ${gesamtwertHand(handSpieler)}")
        return false
    }else if(eingabe == "stand") {
        println("\nDu hast dich dazu entschieden aufzudecken!\nDer Computer versucht jetzt sein Glück.")
        var computerWert = gesamtwertHand(handComputer)

        while (computerWert < 17) {
            computerWert = hitComputer(deck, handComputer)
        }

        return true
    }else{
        println("Du hast eine falsche Eingabe getätigt!")
        return false
    }
}

fun hitComputer(deck: MutableList<String>, handComputer: MutableList<String>): Int{
    if(gesamtwertHand(handComputer) < 17){
        var gezogeneKarte = eineKarteZiehen(deck)
        handComputer.add(gezogeneKarte)
        println("\nDie neue stärkere Karte des Computers hat einen Wert von: ${wertGroessterKarte(handComputer)}")
        return gesamtwertHand(handComputer)
    }else if(gesamtwertHand(handComputer) >= 17){
        println("Der Computer deckt jetzt auf!")
        println("Karten Computer:\n")
        kartenAuflisten(handComputer)
        return 100
    }

    return 100
}

fun wertGroessterKarte(hand: MutableList<String>): Int{
    var groessterKartenWert = kartenWert(hand[0])

    for(karte in hand){
        if(kartenWert(karte) > groessterKartenWert){
            groessterKartenWert = kartenWert(karte)
        }
    }

    return groessterKartenWert
}

fun spielstandUeberpruefen(hand: MutableList<String>, handPlayer: MutableList<String>){
    if(gesamtwertHand(handPlayer) > 21){
        println("\n\nDas Spiel ist vorbei.\nDu hast leider verloren!")
    }
}
fun kartenAuflisten(hand: MutableList<String>){
    for(karte in hand){
        println("Karte: $karte | Wert: ${kartenWert(karte)}")
    }
}