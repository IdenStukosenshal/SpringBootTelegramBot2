package java_tg_bot_2.config;

public enum ConstantsStorage {
    ERROR_TEXT("Message NOT sent, Error: "), START_TXT("PLACEHOLDER FOR /start"),
    HELP_TXT("type /start to start\n type /help to help\n type /remind_me to set remind message\n"
            + "type /exchange_rates to find out the exchange rates of some currencies\n"
            + "type /delete_last_reminder to delete the last reminder\n"
            + "press \"jump\" to jump"),
    REMIND_ME_TXT("формат:\n /remind_me (1y 1m 1d 1h 1min) text text text. \n"
            + "После команды /remind_me укажите время в скобках через пробел, число и единица измерения должны быть написаны слитно,"
            + " количество и порядок не важны, например (1d 1h), (100h 1y 1min). " + "\n"
            + "Если будет указано несколько одинаковых вариантов (1d 2d) будет записан первый." + "\n"
            + "Сообщение будет отправлено обратно через указанное время" + "\n"
            + "(Если зажать пальцем команду, она вставится без отправки)"
    ),
    UNKNOWN_COM_TXT("unknown command"),
    CHOICE_TXT("Выберите монету"),
    COIN_CODE_BASE("USD"),
    EMPTY_REMINDER_LIST("No messages to delete");


    private final String text;

    ConstantsStorage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}