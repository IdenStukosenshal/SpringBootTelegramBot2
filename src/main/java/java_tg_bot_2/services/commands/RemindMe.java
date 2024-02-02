package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
import java_tg_bot_2.models.ReminderMessage;
import java_tg_bot_2.repositories.ReminderMsgRepo;
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
        } else if (messageText.startsWith(CommandsStorage.RemindMe.getText())) {
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
        String year = "y", month = "m", day = "d", hour = "h", minute = "min";

        Long userId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        Map<String, Integer> deltaTimeMap = identifyTime(messageText);//Распознать промежуток времени в скобках

        LocalDateTime ldatetimeNow = LocalDateTime.now();
        LocalDateTime futureTime = ldatetimeNow
                .plusYears(deltaTimeMap.get(year))
                .plusMonths(deltaTimeMap.get(month))
                .plusDays(deltaTimeMap.get(day))
                .plusHours(deltaTimeMap.get(hour))
                .plusMinutes(deltaTimeMap.get(minute));

        String reminderText = "text:\n" + messageText.substring(messageText.indexOf(")") + 1) + "\n";

        ReminderMessage reminderMessage = new ReminderMessage(null, userId, reminderText, futureTime, ldatetimeNow);
        reminderMsgRepo.save(reminderMessage);//Сохранить в БД

        //Формирование ответа после сохранения сообщения
        String[] responseWordsTimes = {" Years, ", " Months, ", " Days, \n", " Hours, ", " Min's.\n"};
        String[] responseLitersTimes = {year, month, day, hour, minute};
        StringBuilder finalResponse = new StringBuilder();
        for (int i = 0; i < responseWordsTimes.length; i++) {
            finalResponse.append(deltaTimeMap.get(responseLitersTimes[i]) != 0 ? deltaTimeMap.get(responseLitersTimes[i]) + responseWordsTimes[i] : "");
        } //если значение == 0, оно не упоминается в ответе
        return "Successfully saved and will be sent via: " + finalResponse + "~ 1-2 minutes before";
    }

    private Map<String, Integer> identifyTime(String messageText) {
        String year = "y", month = "m", day = "d", hour = "h", minute = "min";
        Map<String, Integer> dictRezult = new HashMap<>(Map.of(year, 0, month, 0, day, 0, hour, 0, minute, 0));

        if (!messageText.contains("(") && !messageText.contains(")")) return dictRezult;

        String dateTime = messageText.substring(messageText.indexOf("(") + 1, messageText.indexOf(")")).toLowerCase();
        String[] massiveToParse = dateTime.split(" ");  //{"1y", "1m", "1d", "1h", "1min"}
        System.out.println(dateTime);
        for (String letter : dictRezult.keySet()) {
            for (String word : massiveToParse) {
                if (word.contains(letter)) {
                    String cifr = word.replaceAll(letter, "");
                    if (cifr.matches("[0-9]+")) { //если строка состоит из цифр
                        dictRezult.put(letter, Integer.parseInt(cifr));
                        break;
                    }
                }
            }
        }
        return dictRezult;
    }
}
