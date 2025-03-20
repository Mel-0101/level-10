
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Hand {

    private final ArrayList<Card> hand;
    private final int handsize;
    private final Board board;
    private final Deck deck;

    public Hand(Deck deck, Board board) {
        this.handsize = 10;
        this.deck = deck;
        this.hand = new ArrayList<>();
        for (int i = 0; i < handsize; i++) {
            this.hand.add(deck.draw());
        }
        this.sort();
        this.board = board;
    }

    public void sort() {
        this.hand.sort(Comparator.comparing(Card::getWorld).thenComparingInt(Card::getNumber));
    }


    // TODO Gewonnen/Verloren

    public void playCard(int index) throws IOException {
        if (index >= 0 && index < this.hand.size()) {
            // Methode placeCard aufrufen und Karte übergeben
            this.board.placeCard(this.hand.remove(index));
            // neue Karte ziehen und alte ersetzen, falls der Stapel noch Karten hat
            if (this.deck.getStackSize() > 0) {
                var newCard = this.deck.draw();
                this.hand.add(newCard);
                System.out.println("Neu gezogen: " + newCard);
                // Handkarten sortieren
                this.sort();
            } else {
                System.err.println("Der Stapel ist leer");
            }
        } else {
            System.err.println("Ungültiger Index.");
        }
        // Handkarten anzeigen
        showHandStack();
    }

    public void playResetCard(int world, int... index) throws IOException {
        System.out.println("Spiele ResetCard: " + Board.WORLD_STRINGS[world]);

        this.board.updateBoardResetCard(world);

        for (int i : index) {
            Card exchangedCard = this.hand.remove(i);
            System.out.println("Lege Karte ab: " + exchangedCard);
            Card newCard = this.deck.exchangeCard(exchangedCard);
            this.hand.add(newCard);
            System.out.println("Neu gezogen: " + newCard);
        }

        this.sort();
        // Handkarten sortieren
        this.showHandStack();
        this.board.printBoard();
    }

    public void showHandStack() {
        String array = "";
        int i = 0;
        for (Card c : this.hand) {
            array += "[%s]%s  ".formatted(i, c);
            i++;
        }
        System.out.println("Handkarten: " + array + "\t Nachziehstapel: " + this.deck.getStackSize());
        //        System.out.println("Index: \t\t  0  |  1 |  2 |  3 |  4 |  5 |  6 |  7  | 8  |  9");
    }

    @Override
    public String toString() {
        return this.hand.toString();
    }
}
