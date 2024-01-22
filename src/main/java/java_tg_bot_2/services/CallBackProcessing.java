package java_tg_bot_2.services;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallBackProcessing {
    SendMessage processing(Update update){
        long chatId = update.getMessage().getChatId();
        return new SendMessage(String.valueOf(chatId), "PLACEHOLDER FOR CallBackProcessing");
    }
}
