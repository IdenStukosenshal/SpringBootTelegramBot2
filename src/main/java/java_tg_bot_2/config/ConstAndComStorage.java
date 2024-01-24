package java_tg_bot_2.config;

public enum ConstAndComStorage {
    UNKNOWN_COMMAND("unknown command"), ERROR_TEXT("Message NOT sent, Error: "),
    START("/start"), START_TXT("PLACEHOLDER FOR /start"), HELP("/help"),
    HELP_TXT("type /start to start \n type /help to help\n press \"jump\" to jump"),
    GetRandJoke("/Get_random_joke"), ExchangeRate("/Exchange_rates");

    private String text;
    ConstAndComStorage(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public static final ConstAndComStorage[] BUTTONS_ROW1 = {ExchangeRate, GetRandJoke};
}