
import java.util.Arrays;
import java.util.Random;

public class Deck {

    private Card[] deck;
    private int next; // Position für die nächste Karte vom Nachziehstapel

    public Deck() {

        deck = new Card[40];

        for (int number = 1, i = 0; number <= 8; number++, i++) {
            // Place cards in order in deck, shuffle later
            deck[i] = new Card(0, number); // Sky - 0
            deck[i+8] = new Card(1, number); // Forest - 1
            deck[i+16] = new Card(2, number); // Swamp - 2
            deck[i+24] = new Card(3, number); // Volcano - 3
            deck[i+32] = new Card(4, number); // Desert - 4
        }

        this.next = 0;
    }

    public int getStackSize() {
        return this.deck.length - next;
    }

    public void shuffle() {
        int seed = 3;
        Random randomNumber = new Random(seed);

        for (int card = 0; card < this.deck.length; card++) {
            // Find a random place in the deck
            int rand = randomNumber.nextInt(0,40);

            // Swap cards in deck
            Card temp = deck[card]; // Card from random position
            deck[card] = deck[rand];
            deck[rand] = temp;
        }
    }

    public Card draw() {
        if (this.next < this.deck.length) {
            Card c = this.deck[this.next];
            this.deck[next] = null;
            this.next++;
            return c;
        }
        System.err.println("Der Stapel ist leer.");
        return null;
    }

    public void updateDeck(Card card) {
        // Schneidet Elemente mit null ab, falls welche vorhanden sind (zu sehen an this.next > 0)
        if (this.next != 0) {
            this.deck = Arrays.copyOfRange(this.deck, next, this.deck.length);
            this.next = 0;
        }

        // Überschreibt Array neu mit Größe + 1
        this.deck = Arrays.copyOf(this.deck, this.deck.length+1);

        // Platziert die übergebene Karte am Ende des Decks
        this.deck[this.deck.length-1] = card;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.deck);
    }
}
