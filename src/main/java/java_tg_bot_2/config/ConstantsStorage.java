package java_tg_bot_2.config;

public enum ConstantsStorage {
    ERROR_TEXT("Message NOT sent, Error: "), START_TXT("PLACEHOLDER FOR /start"),
    HELP_TXT("type /start to start \n type /help to help\n press \"jump\" to jump"),
    UNKNOWN_COM_TXT("unknown command");

    private final String text;

    ConstantsStorage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}