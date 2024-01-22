package java_tg_bot_2.services;

import java_tg_bot_2.config.BotConfig;
import java_tg_bot_2.config.ConstAndComStorage;
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
    private final CommandProcessing commandProcessing;
    private final CallBackProcessing callBackProcessing;

    @Autowired
    public MainService(BotConfig botConfig, CallBackProcessing callBackProcessing, CommandProcessing commandProcessing) {
        this.botConfig = botConfig;
        this.commandProcessing = commandProcessing;
        this.callBackProcessing = callBackProcessing;
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
        String messageText;
        long chatId;

        if (update.hasMessage() && update.getMessage().hasText()) {
            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            if (messageText.startsWith("/")) {
                simpleSendMessage(commandProcessing.processing(update));
            } else {
                simpleSendMessage(new SendMessage(String.valueOf(chatId), ConstAndComStorage.UNKNOWN_COMMAND));
            }

        } else if (update.hasCallbackQuery()) {
            simpleSendMessage(callBackProcessing.processing(update));
        }
    }

    //Простой метод отправки уже готовых сообщений
    private void simpleSendMessage(SendMessage msg) {
        try {
            execute(msg);
        } catch (TelegramApiException ee) {
            log.error(ConstAndComStorage.ERROR_TEXT + ee.getMessage());
        }
    }
}
