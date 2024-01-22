package java_tg_bot_2.services;

import java_tg_bot_2.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
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

    //deprecated method, но без него не работает
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        proverka(update);
    }


    private void proverka(Update update) {
        String messageText;
        long chatId; //для взаимодействия с нужным чатом

        if (update.hasMessage() && update.getMessage().hasText()) {
            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                SendMessage message = new SendMessage(); //tg класс
                message.setChatId(String.valueOf(chatId)); //принимает String
                message.setText("ПРИВЕТ");
                try {
                    execute(message);
                } catch (TelegramApiException ee) {
                    log.error("Error occurred: " + ee.getMessage());
                }
            }
        }
    }


}
