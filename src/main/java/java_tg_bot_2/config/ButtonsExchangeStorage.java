package java_tg_bot_2.config;

public enum ButtonsExchangeStorage {
    BTC("BTC"), ETH("ETH"), DOGE("DOGE"), XMR("XMR"), ZANO("ZANO");
    private final String name;

    ButtonsExchangeStorage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
