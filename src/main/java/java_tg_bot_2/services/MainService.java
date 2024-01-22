package java_tg_bot_2.services;

import java_tg_bot_2.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MainService extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    @Autowired
    public MainService(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }


}
