package java_tg_bot_2.config;

public enum CommandsStorage {
    START("/start"), HELP("/help"), RemindMe("/remind_me"), ExchangeRate("/exchange_rates"),
    DELETE_LAST_REMINDER("/delete_last_reminder");
    private final String text;

    CommandsStorage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static final CommandsStorage[] BUTTONS_ROW1 = {ExchangeRate, RemindMe};
}
