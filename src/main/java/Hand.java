
import java.io.IOException;
import java.util.Arrays;

public class Hand {

    private final Card[] hand;
    private final int handsize;
    private final Board board;
    private final Deck deck;

    public Card[] getHand() {
        return hand;
    }

    public Hand(Deck deck, Board board) {
        this.handsize = 10;
        this.deck = deck;
        this.hand = new Card[handsize];
        for (int i = 0; i < handsize; i++) {
            this.hand[i] = deck.draw();
        }
        this.sort();
        this.board = board;
    }

    public void sort() {

        Card temp;

        // Sortieren nach Welten
        for (int n = handsize; n > 1; n--){
            for (int i = 0; i < n-1; i++) {
                if (this.hand[i].getWorld() > this.hand[i+1].getWorld()) {
                    temp = this.hand[i];
                    this.hand[i] = this.hand[i+1];
                    this.hand[i+1] = temp;
                }
            }
        }
        // Sortieren nach Nummern
        for (int n = handsize; n > 1; n--){
            for (int i = 0; i < n-1; i++) {
                if (this.hand[i].getWorld() == this.hand[i+1].getWorld()) {
                    if (this.hand[i].getNumber() > this.hand[i + 1].getNumber()) {
                        temp = this.hand[i];
                        this.hand[i] = this.hand[i + 1];
                        this.hand[i + 1] = temp;
                    }
                }
            }
        }
    }


    // TODO Gewonnen/Verloren

    public void playCard(int index) throws IOException {
        if (index >= 0 && index < this.hand.length) {
            Card card = this.hand[index];
            // System.out.println("Spiele Karte: " + card); <- ist jetzt in board.placeCard()
            // Methode placeCard aufrufen und Karte übergeben
            this.board.placeCard(card);
            // neue Karte ziehen und alte ersetzen, falls der Stapel noch Karten hat
            if (this.deck.getStackSize() > 0) {
                this.hand[index] = this.deck.draw();
                System.out.println("Neu gezogen: " + this.hand[index]);
                // Handkarten sortieren
                this.sort();
            } else {
                System.err.println("Der Stapel ist leer");
                this.hand[index] = null;
            }
        }
        else {
            System.err.println("Ungültiger Index.");
        }
        // Handkarten anzeigen
        showHandStack();
    }

    public void playResetCard(int world, int... index) throws IOException {
        System.out.println("Spiele ResetCard: " + this.board.worldString[world]);

        this.board.updateBoardResetCard(world);

        // für jede abgelegte Handkarte wird die Methode updateDeck aufgerufen und die Handkarte übergeben
        for (int i : index) {
            System.out.println("Lege Karte ab: " + this.hand[i]);
            this.deck.updateDeck(this.hand[i]);
        }
        // für jede abgelegte Handkarte wird eine neue Karte gezogen
        for (int i : index) {
            this.hand[i] = this.deck.draw();
            System.out.println("Neu gezogen: " + this.hand[i]);
            this.sort();
        }
        // Handkarten sortieren
        this.showHandStack();
        this.board.printBoard();
    }

    public void showHandStack() {
        String array = "";
        int i = 0;
        for (Card c : this.hand) {
            array += "["+i+"]" + c + "  ";
            i++;
        }
        System.out.println("Handkarten: " + array + "\t Nachziehstapel: " + this.deck.getStackSize());
        //        System.out.println("Index: \t\t  0  |  1 |  2 |  3 |  4 |  5 |  6 |  7  | 8  |  9");
    }

    @Override
    public String toString() {
        return Arrays.toString(hand);
    }
}
