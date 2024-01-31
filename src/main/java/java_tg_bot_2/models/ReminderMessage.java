package java_tg_bot_2.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReminderMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //автогенерация id напоминания
    private Long id;

    private Long userId;

    private String text;

    private LocalDateTime timeToRemind;
    private LocalDateTime createdAt;
}
