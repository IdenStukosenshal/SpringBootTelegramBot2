package javaTgBot_2.services.commands;

import javaTgBot_2.config.CommandsStorage;
import javaTgBot_2.config.ConstantsStorage;
import javaTgBot_2.models.ReminderMessage;
import javaTgBot_2.repositories.ReminderMsgRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class RemindMe implements CommandIntrf {

    private final ReminderMsgRepo reminderMsgRepo;
    private final StartComm startComm;

    private final String year = "y", month = "m", day = "d", hour = "h", minute = "min";
    private final String[] responseLitersTimes = {year, month, day, hour, minute};

    public RemindMe(ReminderMsgRepo reminderMsgRepo, StartComm startComm) {
        this.reminderMsgRepo = reminderMsgRepo;
        this.startComm = startComm;
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        if (messageText.equals(CommandsStorage.REMIND_ME.getText())) {
            return new SendMessage(String.valueOf(chatId), ConstantsStorage.REMIND_ME_TXT.getText());
        } else if (messageText.startsWith(CommandsStorage.REMIND_ME.getText())) {
            String statusText = saveRemindMessage(update);
            return new SendMessage(String.valueOf(chatId), statusText);
        }
        return new SendMessage(String.valueOf(chatId), ConstantsStorage.UNKNOWN_COM_TXT.getText());
    }

    @Override
    public CommandsStorage getCommandName() {
        return CommandsStorage.REMIND_ME;
    }

    private String saveRemindMessage(Update update) {
        startComm.registerUser(update);

        Long userId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        Map<String, Integer> deltaTimeMap = identifyTime(messageText);//Распознать промежуток времени в скобках

        LocalDateTime ldatetimeNow = LocalDateTime.now();
        LocalDateTime futureTime = ldatetimeNow
                .plusYears(deltaTimeMap.getOrDefault(year, 0))
                .plusMonths(deltaTimeMap.getOrDefault(month, 0))
                .plusDays(deltaTimeMap.getOrDefault(day, 0))
                .plusHours(deltaTimeMap.getOrDefault(hour, 0))
                .plusMinutes(deltaTimeMap.getOrDefault(minute, 0));

        String reminderText = "text:\n";
        if (messageText.contains(")")) { //если формат правильный, то текст после скобочки, если нет, то после первого пробела
            reminderText += messageText.substring(messageText.indexOf(")") + 1) + "\n";
        } else reminderText += messageText.substring(messageText.indexOf(" ") + 1) + "\n";

        ReminderMessage reminderMessage = new ReminderMessage(null, userId, reminderText, futureTime, ldatetimeNow);
        reminderMsgRepo.save(reminderMessage);//Сохранить в БД

        //Формирование ответа после сохранения сообщения
        String[] responseWordsTimes = {" Years, ", " Months, ", " Days, \n", " Hours, ", " Min's.\n"};
        StringBuilder finalResponse = new StringBuilder();
        for (int i = 0; i < responseWordsTimes.length; i++) {
            finalResponse.append(deltaTimeMap.getOrDefault(responseLitersTimes[i], 0) != 0 ? deltaTimeMap.get(responseLitersTimes[i]) + responseWordsTimes[i] : "");
        } //если значение == 0, оно не упоминается в ответе
        return "Successfully saved and will be sent via: " + finalResponse + "~ 1-2 minutes before";
    }

    private Map<String, Integer> identifyTime(String messageText) {
        // формат будет такой например "/Remind_Me (1y 1m 1d 3h 1min) text text text"
        //Если год, месяц и тд не нужны их не указывают, если указано несколько раз - учитывается только первый
        //например "/Remind_Me (2h 1min) text" - через 2 часа 1 минуту
        Map<String, Integer> dictRezult = new HashMap<>();

        if (!messageText.contains("(") || !messageText.contains(")")) return dictRezult;

        String dateTimeStr = messageText.substring(messageText.indexOf("(") + 1, messageText.indexOf(")")).toLowerCase();
        String[] massiveToParse = dateTimeStr.split(" ");  //{"1y", "1m", "1d", "1h", "1min"}
        for (String letter : responseLitersTimes) {
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
