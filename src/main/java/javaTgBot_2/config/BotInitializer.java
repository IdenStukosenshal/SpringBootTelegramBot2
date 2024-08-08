package javaTgBot_2.config;

import org.slf4j.Logger;
import javaTgBot_2.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/*
 private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BotInitializer.class);
*/

@Component
public class BotInitializer {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BotInitializer.class);

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
