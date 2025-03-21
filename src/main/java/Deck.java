
import java.util.*;

public class Deck {

    private final ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>();

        for (int number = 1, i = 0; number <= 8; number++, i++) {
            this.deck.add(new Card(World.SKY, number));
            this.deck.add(new Card(World.FOREST, number));
            this.deck.add(new Card(World.SWAMP, number));
            this.deck.add(new Card(World.VOLCANO, number));
            this.deck.add(new Card(World.DESERT, number));
        }
        Collections.shuffle(deck);
    }

    public int getStackSize() {
        return this.deck.size();
    }

    public Card draw() {
        if (!deck.isEmpty()) {
            return deck.removeFirst();
        } else return null;
    }

    /**
     * Places the discarded card under the card deck and draws a new one into hand.
     * @param card discarded card
     * @return new drawn card
     */
    public Card exchangeCard(Card card) {
        this.deck.addLast(card);
        return draw();
    }

    @Override
    public String toString() {
        return this.deck.toString();
    }
}
