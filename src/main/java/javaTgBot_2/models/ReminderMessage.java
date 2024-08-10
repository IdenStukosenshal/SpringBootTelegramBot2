package javaTgBot_2.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;


public class ReminderMessage {

    @Id
    private Long id;

    private Long chatId;

    private String text;

    private LocalDateTime timeToRemind;
    private LocalDateTime createdAt;

    public ReminderMessage() {
    }

    public ReminderMessage(Long id, Long chatId, String text, LocalDateTime timeToRemind, LocalDateTime createdAt) {
        this.id = id;
        this.chatId = chatId;
        this.text = text;
        this.timeToRemind = timeToRemind;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimeToRemind() {
        return timeToRemind;
    }

    public void setTimeToRemind(LocalDateTime timeToRemind) {
        this.timeToRemind = timeToRemind;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ReminderMessage{" +
                "id=" + id +
                ", userId=" + chatId +
                ", text='" + text + '\'' +
                ", timeToRemind=" + timeToRemind +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReminderMessage that = (ReminderMessage) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getChatId(), that.getChatId())
                && Objects.equals(getText(), that.getText())
                && Objects.equals(getTimeToRemind(), that.getTimeToRemind())
                && Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChatId(), getText(), getTimeToRemind(), getCreatedAt());
    }
}
