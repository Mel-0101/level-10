
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scan;
    private final Board board;
    private final Deck deck;
    private final Hand hand;

    public UserInterface() {
        this.scan = new Scanner(System.in);

        // Erzeugen & Ausgeben des Spielfelds
        this.board = new Board();
        this.board.printBoard();

        // Erzeugen & Mischen des Nachziehstapels
        this.deck = new Deck();
        this.deck.shuffle();

        // Erzeugen & Sortieren der Starthand
        this.hand = new Hand(this.deck, this.board);
        //        System.out.println("Handkarten: " + this.hand);
        this.hand.showHandStack();
    }

    public void readInput(){
        String inputStr;
        while( true ){
            System.out.println();
            System.out.print("Spiele eine Handkarte (Index 0...9) oder eine Reset Card (R0...R4) oder : ");
            inputStr = this.scan.nextLine();
            if(inputStr.strip().equalsIgnoreCase("beenden")) {
                System.out.println("Das Spiel wird nun beendet");
                break;
            } else {
                analyzeInput(inputStr);
            }
            //            try{
            //                analysiereEingabe(eingabe);
            //                if(ausgabe.equals("beenden")){
            //                    System.out.println("Das Programm wird nun beendet");
            //                    break;
            //                }else{
            //                    System.out.println(ausgabe);
            //                }
            //            }catch(Exception e){
            //                System.out.println(e.getMessage());
            //            }
        }
    }

    public void analyzeInput(String input) {
        switch (input) {
            case "0","1","2","3","4","5","6","7","8","9":
                int index = Integer.parseInt(input);
                try {
                    this.hand.playCard(index);
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "R0", "R1", "R2", "R3", "R4":
                int world = Integer.parseInt(input.substring(1));
                if (this.board.checkResetCards()) { // in keiner Spalte eine 0?
                    try {
                        analyzeResetCard(world);
                        scan.nextLine(); // Um ein Enter abzufangen
                    } catch (IOException | IndexOutOfBoundsException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.err.println("Du darfst keine ResetCard legen, wenn in dieser Spalte bereits eine liegt!");
                }
                break;
            default:
                System.err.println("Keine gültige Eingabe!");
                break;
        }
    }

    public void analyzeResetCard(int world) throws IOException, IndexOutOfBoundsException {
        System.out.print("Wähle eine Anzahl von 0-2 Handkarten, die du austauschen möchtest: ");
        int cardsNumber = this.scan.nextInt();

        switch (cardsNumber) {
            case 0:
                this.hand.playResetCard(world);
                break;
            case 1:
                System.out.println("Wähle die Handkarte (Index 0..9): ");
                int index = this.scan.nextInt();
                if (index >= 0 && index < 10) {
                    this.hand.playResetCard(world, index);
                } else {
                    throw new IndexOutOfBoundsException("Der Index muss zwischen 0 und 9 liegen!");
                }
                break;
            case 2:
                System.out.println("Wähle die zwei Handkarten (Index 0..9): ");
                int index1 = this.scan.nextInt();
                int index2 = this.scan.nextInt();
                if ((index1 >= 0 && index1 < 10) && (index2 >= 0 && index2 < 10)) {
                    this.hand.playResetCard(world, index1, index2);
                } else {
                    throw new IndexOutOfBoundsException("Der Index muss zwischen 0 und 9 liegen!");
                }
                break;
            default:
                System.err.println("Keine gültige Eingabe!");
                break;
        }
    }

    public void closeProgram(){
        if(this.scan != null ){
            this.scan.close();
        }
    }

}
