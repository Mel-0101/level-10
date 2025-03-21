

public class Card {

    private final World world;
    private final int number;   // 1-8

    public Card(World world, int number) {
        this.world = world;
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public String toString() {
        return this.world.getSymbol() + this.number;
    }
}
