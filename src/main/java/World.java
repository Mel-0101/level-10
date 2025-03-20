public enum World {
    SKY(0, "☁\uFE0F"),
    FOREST(1, "\uD83C\uDF43"),
    SWAMP(2, "\uD83D\uDC80"),
    VOLCANO(3, "\uD83C\uDF0B"),
    DESERT(4, "☀\uFE0F");
    private final int value;
    private final String symbol;

    World(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }
}
