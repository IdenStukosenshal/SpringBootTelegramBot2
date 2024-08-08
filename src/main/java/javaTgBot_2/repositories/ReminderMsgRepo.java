package javaTgBot_2.repositories;

import javaTgBot_2.models.ReminderMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReminderMsgRepo extends CrudRepository<ReminderMessage, Long> {


    Optional<ReminderMessage> findFirstByUserIdOrderByCreatedAtDesc(Long userId);
    //Метод вернет самое последнее сообщение для данного пользователя
    //Spring интерпретирует имя метода как SQL запрос
    //DSL Spring Data

    /*
        ПЕРЕДЕЛАТЬ после создания таблиц
    @Query("SELECT ????? FROM ReminderMessage r WHERE r.userId = :userId ORDER BY r.createdAt DESC")
    Optional<ReminderMessage> findLastByUserId(@Param("userId") Long userId);

     */
}
