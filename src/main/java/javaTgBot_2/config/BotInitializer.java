package javaTgBot_2.config;


import javaTgBot_2.services.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/*
 добавляет
 private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BotInitializer.class);
*/
@Slf4j
@Component
public class BotInitializer {

    private final MainService mainService;

    @Autowired
    public BotInitializer(MainService mainService) {
        this.mainService = mainService;
    }


    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(mainService);
        } catch (TelegramApiException eeee) {
            log.error("Error occurred: " + eeee.getMessage());
        }

    }
}
