
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scan;
    private final Board board;
    private final Hand hand;

    public UserInterface() {
        this.scan = new Scanner(System.in);

        this.board = new Board();
        this.board.printBoard();

        Deck deck = new Deck();

        this.hand = new Hand(deck, this.board);
        this.hand.printHand();
    }

    public void readInput() {
        String inputString;
        while (this.board.isWinning) {
            System.out.println();
            System.out.printf("Play a hand card in ascending order (index 0...9) or a Reset Card " +
                    "(R0=%s|R1=%s|R2=%s|R3=%s|R4=%s) or type quit: ",
                    World.SKY.getSymbol(), World.FOREST.getSymbol(), World.SWAMP.getSymbol(),
                    World.VOLCANO.getSymbol(), World.DESERT.getSymbol());
            inputString = this.scan.nextLine();
            if (inputString.strip().equalsIgnoreCase("quit")) {
                break;
            } else {
                analyzeInput(inputString);
            }
        }
    }

    public void closeProgram() {
        if (this.scan != null) {
            this.scan.close();
        }
    }

    private void analyzeInput(String input) {
        switch (input) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                int index = Integer.parseInt(input);
                try {
                    this.hand.playCard(index);
                } catch (IOException e) {
                    System.err.println((e.getMessage()));
                }
            }
            case "R0", "R1", "R2", "R3", "R4" -> {
                int world = Integer.parseInt(input.substring(1));
                if (this.board.canPlaceResetCard()) { // no Reset Card played in current column?
                    try {
                        processResetCard(world);
                        scan.nextLine(); // to catch an Enter
                    } catch (IOException | IndexOutOfBoundsException e) {
                        System.err.println((e.getMessage()));
                    }
                } else {
                    System.err.println("You may not place a reset card if there is already one in the current column.");
                }
            }
            default -> System.err.println("Invalid input.");
        }
    }

    private void processResetCard(int world) throws IOException, IndexOutOfBoundsException {
        System.out.print("Choose a number of 0-2 cards from your hand that you want to place under the pile and " +
                "for which you will draw new cards: ");
        int cardsNumber = this.scan.nextInt();

        switch (cardsNumber) {
            case 0 -> this.hand.playResetCard(world);
            case 1 -> {
                System.out.println("Choose the hand card you want to discard (Index 0..9): ");
                int index = this.scan.nextInt();
                if (index >= 0 && index < 10) {
                    this.hand.playResetCard(world, index);
                } else {
                    throw new IndexOutOfBoundsException("The index must be between 0 and 9.");
                }
            }
            case 2 -> {
                System.out.println("Choose the two hand cards you want to discard (Index 0..9): ");
                int index1 = this.scan.nextInt();
                int index2 = this.scan.nextInt();
                if ((index1 >= 0 && index1 < 10) && (index2 >= 0 && index2 < 10)) {
                    this.hand.playResetCard(world, index1, index2);
                } else {
                    throw new IndexOutOfBoundsException("The index must be between 0 and 9.");
                }
            }
            default -> System.err.println("Invalid input.");
        }
    }
}


