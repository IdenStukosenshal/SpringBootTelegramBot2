package javaTgBot_2.config;

public enum ButtonsExchangeStorage {
    BTC("BTC"), ETH("ETH"), DOGE("DOGE");//, ZANO("ZANO"), XMR("XMR") монеты были удалены
    private final String name;

    ButtonsExchangeStorage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
