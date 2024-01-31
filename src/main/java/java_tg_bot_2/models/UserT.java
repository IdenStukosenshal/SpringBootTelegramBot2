package java_tg_bot_2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data/* Эта аннотация добавляет equals(), hashCode(), toString(),
конструктор, принимающий значения, сеттеры и геттеры
*/
@NoArgsConstructor
@AllArgsConstructor
public class UserT {
    @Id
    private Long chatId;

    private String firstName;
    private String userName;

    private LocalDateTime registeredAt;

}
