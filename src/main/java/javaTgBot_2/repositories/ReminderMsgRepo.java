package javaTgBot_2.repositories;

import javaTgBot_2.models.ReminderMessage;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReminderMsgRepo extends CrudRepository<ReminderMessage, Long> {


    //Метод вернет самое последнее сообщение для данного пользователя
    @Query("SELECT *" +
           "  FROM reminder_message " +
            "WHERE chat_id = :chatId AND created_at = (SELECT MAX(created_at) FROM reminder_message WHERE chat_id = :chatId)")
    Optional<ReminderMessage> findLastByUserId(Long chatId);


    /*
    SELECT *
      FROM reminder_msg
     WHERE chatId = :chatId AND created_at = (SELECT MAX(created_at) FROM reminder_msg WHERE chatId = :chatId)
     */
}
