package javaTgBot_2.repositories;

import javaTgBot_2.models.UserT;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

//Create, Read, Update, Delete
public interface UserRepo extends CrudRepository<UserT, Long> {

    @Modifying
    @Query("INSERT INTO user_t(chat_id, first_name, user_name) " +
            "    VALUES (:chatId, :firstName, :userName)")
    void saveUser(long chatId, String firstName, String userName);

}
