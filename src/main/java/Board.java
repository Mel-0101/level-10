
import java.io.IOException;

public class Board {
    String[][] board;
    String[] worldString = {"☁\uFE0F", "\uD83C\uDF43", "\uD83D\uDC80", "\uD83C\uDF0B", "☀\uFE0F"};
    final int columnNumber = 12;
    int currentColumn = 2;

    public Board() {
        this.board = new String[5][columnNumber];

        // Befüllt das Array mit je zwei Nullen ganz vorne
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 2; column++) {
                this.board[row][column] = "0";
            }
        }
        // Befüllt das Array auf den weiteren Plätzen mit "."
        for (int row = 0; row < 5; row++) {
            for (int column = 2; column < columnNumber; column++) {
                this.board[row][column] = ".";
            }
        }
    }

    /**
     * Gibt das zweidimensionale Array des Spielbretts aus. Die erste Zeile besteht aus den Icons der Welten.
     */
    public void printBoard() {
        //        String[] world = {"☁\uFE0F", "\uD83C\uDF43", "\uD83D\uDC80", "\uD83C\uDF0B", "☀\uFE0F"};
        System.out.println("----------------------------");
        for (int zeile = 0; zeile < 5; zeile++) {
            System.out.print(worldString[zeile] + "\t");
            for (int spalte = 0; spalte < 12; spalte++) {
                System.out.print(this.board[zeile][spalte] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------");

    }

    /**
     * Überladene Methode, um eine Handkarte zu platzieren
     * @param card gespielte Handkarte
     */
    public void placeCard(Card card) throws IOException {
        int world = card.getWorld();
        int number = card.getNumber();

        if (this.board[world][currentColumn].equals(".")) { // Prüfen, ob Feld leer ist
            if (Integer.parseInt(this.board[world][currentColumn - 1]) < number) {
                this.board[world][currentColumn] = String.valueOf(number); // Platziert Karte an der richtigen Stelle
                // Prüft Lose-Bedingung, dass keine 0 in der Spalte liegt und setzt wenn nötig den Counter für CurrentColumn hoch
                checkColumn();
            } else {
                throw new IOException("Die Karten müssen aufsteigend gespielt werden!");
            }
        } else {
            throw new IOException("Für diese Welt liegt in dieser Spalte bereits eine Karte!");
        }
        System.out.println("Spiele Karte: " + card);
        printBoard();
    }

    /**
     * Methode, um die ResetCard zu platzieren
     * @param world Index der Welt, in der die ResetCard gelegt werden soll.
     */
    private void placeCard(int world) throws IOException  {
        if (this.board[world][currentColumn].equals(".")) { // Prüfen, ob Feld leer ist
            this.board[world][currentColumn] = "0";         // Platziert ResetCard an der richtigen Stelle
            // Prüft Loose-Bedingung, dass keine 0 in der Spalte liegt und setzt wenn nötig den Counter für CurrentColumn hoch
            checkColumn();
        } else {
            throw new IOException("Die aktuelle Spalte muss zunächst beendet werden.");
        }
        printBoard();
    }

    public void updateBoardResetCard(int world) throws IOException {

        if (this.board[world][0].equals("0")){
            this.board[world][0] = ".";

            // Methodenaufruf: Platziere ResetCard
            placeCard(world);
        }
        else if (this.board[world][1].equals("0")){
            this.board[world][1] = ".";
            // Methodenaufruf: Platziere ResetCard
            placeCard(world);
        }
        else {
            System.err.println("Keine ResetCard für diese Welt mehr verfügbar.");
        }
    }

    public void checkColumn() {
        int emptySpots = 5;
        for (int i = 0; i < 5; i++) {
            if (!this.board[i][this.currentColumn].equals(".")) {
                emptySpots--;
            }
            if (emptySpots == 0) {
                if (!this.board[0][this.currentColumn].equals("0") && !this.board[1][this.currentColumn].equals("0") &&
                        !this.board[2][this.currentColumn].equals("0") && !this.board[3][this.currentColumn].equals("0") &&
                        !this.board[4][this.currentColumn].equals("0")) {
                    System.err.println("Es fehlt eine 0 in der aktuellen Spalte. Du hast verloren!");
                }
                this.currentColumn++;
            }
        }
    }

    public boolean checkResetCards() {
        // return true, wenn ich keiner Zeile der aktuellen Spalte eine 0 liegt.
        boolean valid = !this.board[0][this.currentColumn].equals("0") && !this.board[1][this.currentColumn].equals("0") &&
                !this.board[2][this.currentColumn].equals("0") && !this.board[3][this.currentColumn].equals("0") &&
                !this.board[4][this.currentColumn].equals("0");
        return valid;
    }

    // TODO Aufsteigend legen
    // TODO Spalten-Check bevor Karten gezogen werden
}
