
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Hand {

    private final ArrayList<Card> hand;
    private static final int HANDSIZE = 10;
    private final Board board;
    private final Deck deck;

    public Hand(Deck deck, Board board) {
        this.deck = deck;
        this.hand = new ArrayList<>();
        for (int i = 0; i < HANDSIZE; i++) {
            this.hand.add(deck.draw());
        }
        this.sort();
        this.board = board;
    }

    /**
     * If the chosen index is valid, the card is placed into the board by calling the method {@link Board#placeCard(Card)}. <br>
     * A new card is drawn and printed on the screen.<br>
     * Additionally, the whole updated hand is printed to the screen.
     * @param index index of played hand card
     * @throws IOException
     */
    public void playCard(int index) throws IOException {
        if (index >= 0 && index < HANDSIZE) {
            this.board.placeCard(this.hand.remove(index));
            if (this.deck.getStackSize() > 0) {
                var newCard = this.deck.draw();
                this.hand.add(newCard);
                System.out.println("Card drawn: " + newCard);
                this.sort();
            } else {
                System.err.println("The draw deck is empty.");
            }
        } else {
            System.err.println("Invalid index.");
        }
        printHand();
    }

    public void playResetCard(int world, int... index) throws IOException {
        System.out.println("Played Reset Card: " + World.values()[world].getSymbol());

        this.board.updateBoardWithResetCard(world);

        for (int i : index) {
            Card exchangedCard = this.hand.remove(i);
            System.out.println("Discarded: " + exchangedCard);
            Card newCard = this.deck.exchangeCard(exchangedCard);
            this.hand.add(i, newCard);
            System.out.println("Card drawn: " + newCard);
        }
        this.sort();
        this.printHand();
        this.board.printBoard();
    }

    public void printHand() {
        StringBuilder cards = new StringBuilder();
        int index = 0;
        for (Card c : this.hand) {
            cards.append("[%s]%s  ".formatted(index, c));
            index++;
        }
        System.out.printf("Hand cards: %s\t|\t Draw deck: %d%n", cards, this.deck.getStackSize());
    }

    private void sort() {
        this.hand.sort(Comparator.comparing(Card::getWorld).thenComparingInt(Card::getNumber));
    }
}
