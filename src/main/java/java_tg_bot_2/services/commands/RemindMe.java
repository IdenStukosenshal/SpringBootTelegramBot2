package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
import java_tg_bot_2.models.ReminderMessage;
import java_tg_bot_2.repositories.ReminderMsgRepo;
import java_tg_bot_2.services.MainService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class RemindMe implements CommandIntrf {

    private final ReminderMsgRepo reminderMsgRepo;

    public RemindMe(ReminderMsgRepo reminderMsgRepo) {
        this.reminderMsgRepo = reminderMsgRepo;
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        if (messageText.equals(CommandsStorage.RemindMe.getText())) {
            return new SendMessage(String.valueOf(chatId), ConstantsStorage.REMIND_ME_TXT.getText());
        } else if(messageText.startsWith(CommandsStorage.RemindMe.getText())){
            String statusText = saveRemindMessage(update);
            return new SendMessage(String.valueOf(chatId), statusText);
        }
        return new SendMessage(String.valueOf(chatId), ConstantsStorage.UNKNOWN_COM_TXT.getText());
    }

    @Override
    public String getCommandName() {
        return CommandsStorage.RemindMe.getText();
    }

    private String saveRemindMessage(Update update) {
        // формат будет такой например "/Remind_Me (1y 1m 1d 3h 1min) text text text"
        //Если год, месяц и тд не нужны их не указывают
        //например "/Remind_Me (2h 1min) text" - через 2 часа 1 минуту

        Long userId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        //"text:\n" если текста не будет
        String reminderText = "text:\n" + messageText.substring(messageText.indexOf(")") + 1); //Текст сообщения

        int[] deltaTimeMassive = identifyTime(messageText);//Распознать время

        LocalDateTime ldatetimeNow = LocalDateTime.now();
        LocalDateTime futureTime = ldatetimeNow
                .plusYears(deltaTimeMassive[0])
                .plusMonths(deltaTimeMassive[1])
                .plusDays(deltaTimeMassive[2])
                .plusHours(deltaTimeMassive[3])
                .plusMinutes(deltaTimeMassive[4]);

        ReminderMessage reminderMessage = new ReminderMessage(null, userId, reminderText, futureTime, ldatetimeNow);
        reminderMsgRepo.save(reminderMessage);//Сохранить в БД

        return "Успешно сохранено и будет отправлено через: " + "\n"
                + deltaTimeMassive[0] + " Years, "
                + deltaTimeMassive[1] + " Month, "
                + deltaTimeMassive[2] + " Days, " + "\n"
                + deltaTimeMassive[3] + " Hours, "
                + deltaTimeMassive[4] + " Min." + "\n"
                + "~за 1-2 минуты до";
    }

    private int[] identifyTime(String messageText) {
        String dateTime = messageText.substring(messageText.indexOf("(") + 1, messageText.indexOf(")")).toLowerCase();
        String[] massiveToParse = dateTime.split(" ");  //{"1y", "1m", "1d", "1h", "1min"}

        Map<String, Integer> dictRezult = new HashMap<>(Map.of("y", 0, "d", 0, "m", 0, "h", 0, "min", 0));
        String[] dictTimes = {"y", "d", "m", "h", "min"};

        for (String letter : dictTimes) {
            for (int i = 0; i < massiveToParse.length; i++) {
                if (massiveToParse[i].contains(letter)) {
                    String cifr = massiveToParse[i].replaceAll(letter, "");
                    if (cifr.matches("[0-9]+")) { //если строка состоит из цифр
                        dictRezult.put(letter, Integer.parseInt(cifr));  //int y = Integer.parseInt("1")
                        break;
                    }
                }
            }
        }
        System.out.println();
        return new int[]{dictRezult.get("y"), dictRezult.get("m"), dictRezult.get("d"),
                dictRezult.get("h"), dictRezult.get("min"), };
    }
}
