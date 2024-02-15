package javaTgBot_2.config;

import java.util.ArrayList;
import java.util.List;

public enum CommandsStorage {
    START("/start"), HELP("/help"), REMIND_ME("/remind_me"), EXCHANGE_RATES("/exchange_rates"),
    DELETE_LAST_REMINDER("/delete_last_reminder"), SOURCE_CODE("/source_code");
    private final String text;

    CommandsStorage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static final List<CommandsStorage> BUTTONS_ROW1 = new ArrayList<>(List.of(EXCHANGE_RATES, REMIND_ME));
}
