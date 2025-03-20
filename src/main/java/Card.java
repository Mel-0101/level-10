

public class Card {

    private final World world;

    // 1-8
    private final int number;

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
