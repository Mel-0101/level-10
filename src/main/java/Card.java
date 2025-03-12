
public class Card {

    // sky = 0
    // forest = 1
    // swamp = 2
    // volcano = 3
    // desert = 4
    private final int world;

    // 1-8
    private final int number;

    public Card(int world, int number) {
        this.world = world;
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public int getWorld() {
        return world;
    }

    public String getName() {
        String name = "";

        // Konvertiert Karten-Zahl in Karten-Name

        if (this.world == 0)
            name = "☁\uFE0F";
        else if (this.world == 1)
            name = "\uD83C\uDF43";
        else if (this.world == 2)
            name = "\uD83D\uDC80";
        else if (this.world == 3)
            name = "\uD83C\uDF0B";
        else if (this.world == 4)
            name = "☀\uFE0F";

        return name + this.number;
    }

    public String getNameText() {
        String name = "";

        // Konvertiert Karten-Zahl in Karten-Name

        if (this.world == 0)
            name = "Sky: ";
        else if (this.world == 1)
            name = "Forest: ";
        else if (this.world == 2)
            name = "Swamp: ";
        else if (this.world == 3)
            name = "Volcano: ";
        else if (this.world == 4)
            name = "Desert: ";

        return name + this.number;
    }

    @Override
    public String toString() {
        return getName();
    }
}
