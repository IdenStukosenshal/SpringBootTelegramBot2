package javaTgBot_2.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Getter //добавляет геттеры для всех полей
@Configuration
@EnableScheduling
@EnableFeignClients(basePackages = "javaTgBot_2.proxy")
public class BotConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.owner}")
    private Long ownerId;

}
