package javaTgBot_2.repositories;

import javaTgBot_2.models.ReminderMessage;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReminderMsgRepo extends CrudRepository<ReminderMessage, Long> {


    //Метод вернет самое последнее сообщение для данного пользователя
    @Query("SELECT *" +
           "  FROM reminder_message " +
            "WHERE chat_id = :chatId AND created_at = (SELECT MAX(created_at) FROM reminder_message WHERE chat_id = :chatId)")
    Optional<ReminderMessage> findLastByUserId(Long chatId);

    //Возвращение всех сообщений которым осталось меньше одного дня до отправки и удаления
    @Query("SELECT * " +
           "  FROM reminder_message " +
           " WHERE DATEDIFF(time_to_remind, NOW()) = 0")
    List<ReminderMessage> findAllReadyMsg();
}
