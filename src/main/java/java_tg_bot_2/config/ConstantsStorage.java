package java_tg_bot_2.config;

public enum ConstantsStorage {
    ERROR_TEXT("Message NOT sent, Error: "), START_TXT("PLACEHOLDER FOR /start"),
    HELP_TXT("type /start to start \n type /help to help\n press \"jump\" to jump"),
    REMIND_ME_TXT("формат:\n /Remind_Me (1y 1m 1d 1h 1min) text text text. \n"
            + "После команды /Remind_Me укажите время в скобках через пробел, число и единица измерения должны быть написаны слитно,"
            + " количество и порядок не важны, например (1d 1h), (100h 1y 1min). " + "\n"
            + "Если будет указано несколько одинаковых вариантов (1d 2d) будет записан первый." + "\n"
            + "Сообщение будет отправлено обратно через указанное время" +"\n"
            + "(Если зажать пальцем команду, она вставится без отправки)"
            ),
    UNKNOWN_COM_TXT("unknown command"),
    CHOICE_TXT("Выберите монету"),
    COIN_CODE_BASE("USD");


    private final String text;

    ConstantsStorage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}