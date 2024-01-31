package java_tg_bot_2.repositories;

import java_tg_bot_2.models.UserT;
import org.springframework.data.repository.CrudRepository;

//Create, Read, Update, Delete
public interface UserRepo extends CrudRepository<UserT, Long> {
}
