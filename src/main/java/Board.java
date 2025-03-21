
import java.io.IOException;

public class Board {
    public boolean isWinning = true;
    private final String[][] board;
    private static final int COLUMNS = 12;
    private static final int ROWS = 5;
    private int currentColumn = 2;  // Start-Wert

    public Board() {
        this.board = new String[ROWS][COLUMNS];

        // Puts zeros in the first two columns
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < 2; column++) {
                this.board[row][column] = "0";
            }
        }
        // Fills rest of the array with placeholder "."
        for (int row = 0; row < ROWS; row++) {
            for (int column = 2; column < COLUMNS; column++) {
                this.board[row][column] = ".";
            }
        }
    }

    public void printBoard() {
        System.out.println("----------------------------");
        // Iterates through the five Worlds and prints the corresponding symbol in the first column
        for (World w : World.values()) {
            System.out.print(w.getSymbol() + "\t");
            for (int column = 0; column < COLUMNS; column++) {
                System.out.print(this.board[w.ordinal()][column] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------");
    }

    /**
     * Overloaded method for placing card from hand.
     *
     * @param card played hand card
     * @throws IOException
     */
    public void placeCard(Card card) throws IOException {
        int world = card.getWorld().ordinal();
        int number = card.getNumber();

        if (this.board[world][currentColumn].equals(".")) { // check if corresponding field is empty
            if (Integer.parseInt(this.board[world][currentColumn - 1]) < number) {  // check if new placed card is higher than previous card
                this.board[world][currentColumn] = String.valueOf(number);
                checkColumn();  // check if column is finished or if the game is lost
            } else {
                throw new IOException("Cards must be played in ascending order.");
            }
        } else {
            throw new IOException("There is already a card for this world in this column.");
        }
        System.out.println("Played card: " + card);
        printBoard();
    }

    /**
     * Checks if there are still Reset Cards for the chosen worlds available and then moves it to the current column
     * by calling the method {@link #placeCard(int)}.
     * @param world chosen world/row
     * @throws IOException
     */
    public void updateBoardWithResetCard(int world) throws IOException {
        if (this.board[world][0].equals("0")) {
            this.board[world][0] = ".";
            placeCard(world);
        } else if (this.board[world][1].equals("0")) {
            this.board[world][1] = ".";
            placeCard(world);
        } else {
            System.err.println("No more Reset Cards available for this world.");
        }
    }

    public boolean canPlaceResetCard() {
        return !this.board[0][this.currentColumn].equals("0") && !this.board[1][this.currentColumn].equals("0") &&
                !this.board[2][this.currentColumn].equals("0") && !this.board[3][this.currentColumn].equals("0") &&
                !this.board[4][this.currentColumn].equals("0");
    }

    /**
     * Overloaded method for placing Reset Card.
     *
     * @param world row in which the Reset Card is played
     * @throws IOException
     */
    private void placeCard(int world) throws IOException {
        if (this.board[world][currentColumn].equals(".")) { // check if corresponding field is empty
            this.board[world][currentColumn] = "0";         // check if new placed card is higher than previous card
            checkColumn(); // check if column is finished or if the game is lost
        } else {
            throw new IOException("The current column must be finished first.");
        }
    }

    /**
     * Checks if the current column is finished - then raises <i>currentColumn</i>.
     * Additionally, checks if the game is lost through a missing Reset Card.
     */
    private void checkColumn() {
        int emptySpots = 5;
        for (int row = 0; row < ROWS; row++) {
            if (!this.board[row][this.currentColumn].equals(".")) {
                emptySpots--;
            }
            if (emptySpots == 0) {
                if (!this.board[0][this.currentColumn].equals("0") && !this.board[1][this.currentColumn].equals("0") &&
                        !this.board[2][this.currentColumn].equals("0") && !this.board[3][this.currentColumn].equals("0") &&
                        !this.board[4][this.currentColumn].equals("0")) {
                    System.err.println("There is a Reset Card missing in the current column. You lost the game.");
                    lost();
                }
                this.currentColumn++;
            }
        }
    }

    private void lost() {
        this.isWinning = false;
    }
}
