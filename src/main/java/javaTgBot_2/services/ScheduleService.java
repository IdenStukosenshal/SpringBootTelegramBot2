package javaTgBot_2.services;

import javaTgBot_2.models.ReminderMessage;
import javaTgBot_2.repositories.ReminderMsgRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduleService {
    private final MainService mainService;
    private final ReminderMsgRepo reminderMsgRepo;

    public ScheduleService(MainService mainService, ReminderMsgRepo reminderMsgRepo) {
        this.mainService = mainService;
        this.reminderMsgRepo = reminderMsgRepo;
    }

    /*Метод осуществляет проверку списка сообщений через некоторый промежуток времени.
Если подошло время отправить - он это делает
    Если список будет очень большим ВОЗМОЖНО всё перестанет работать
 */
    @Scheduled(cron = "0 * * * * *") // 0 *...* каждую минуту
    //секунды, минуты, часы, дата(номер дня), месяц, день недели
    //* любое значение, 6 звёзд: каждую секунду, 0 0 *...: каждый час в 00:00
    private void checkToSend() {
        List<ReminderMessage> allMsgList = reminderMsgRepo.findAllReadyMsg();
        LocalDateTime ldatetimeNow = LocalDateTime.now();

        for (ReminderMessage savedMsg : allMsgList) {
            LocalDateTime ldatetimeSAVed = savedMsg.getTimeToRemind();
            if (Duration.between(ldatetimeNow, ldatetimeSAVed).toMinutesPart() <= 2) { //Сообщение отправится примерно за 2 минуты до
                SendMessage message = new SendMessage();
                message.setChatId(savedMsg.getChatId());
                message.setText(savedMsg.getText() + "\nhas been saved: " + "\n" + savedMsg.getCreatedAt());
                mainService.simpleSendMessage(message); //отправка сообщения
                reminderMsgRepo.deleteById(savedMsg.getId()); //удаление сообщения из БД
            }
        }
    }

    //Класс Duration для хранения длительности, промежутка
    //https://stackoverflow.com/questions/24491243/why-cant-i-get-a-duration-in-minutes-or-hours-in-java-time
}
