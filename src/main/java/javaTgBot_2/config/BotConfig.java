package javaTgBot_2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableFeignClients(basePackages = "javaTgBot_2.proxy")
public class BotConfig {

    @Value("${BOT_NAME}")
    private String botName;

    @Value("${BOT_TOKEN}")
    private String token;

    @Value("${BOT_OWNER}")
    private Long ownerId;

    public String getBotName() {
        return botName;
    }

    public String getToken() {
        return token;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}
