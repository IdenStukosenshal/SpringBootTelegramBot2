package java_tg_bot_2.repositories;

import java_tg_bot_2.models.ReminderMessage;
import org.springframework.data.repository.CrudRepository;

public interface ReminderMsgRepo extends CrudRepository<ReminderMessage, Long> {
}
