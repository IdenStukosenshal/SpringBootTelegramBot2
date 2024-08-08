package javaTgBot_2.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;


//@Data
 /* Эта аннотация добавляет equals(), hashCode(), toString(),
конструктор, принимающий значения, сеттеры и геттеры
*/

public class UserT {
    @Id
    private Long chatId;

    private String firstName;
    private String userName;
    private LocalDateTime registeredAt;

    public UserT(Long chatId, String firstName, String userName, LocalDateTime registeredAt) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.userName = userName;
        this.registeredAt = registeredAt;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "UserT{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredAt=" + registeredAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserT userT = (UserT) o;
        return Objects.equals(getChatId(), userT.getChatId())
                && Objects.equals(getFirstName(), userT.getFirstName())
                && Objects.equals(getUserName(), userT.getUserName())
                && Objects.equals(getRegisteredAt(), userT.getRegisteredAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId(), getFirstName(), getUserName(), getRegisteredAt());
    }
}
