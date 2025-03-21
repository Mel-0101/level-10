public enum World {
    SKY("☁\uFE0F"),
    FOREST("\uD83C\uDF43"),
    SWAMP("\uD83D\uDC80"),
    VOLCANO("\uD83C\uDF0B"),
    DESERT("☀\uFE0F");
    private final String symbol;

    World(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
