package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.ConstAndComStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


public class StartComm implements CommandIntrf {
    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage msg = new SendMessage(String.valueOf(chatId), ConstAndComStorage.START_TXT);
        msg.setReplyMarkup(addKeyboardToMsg()); //добавление клавиатуры
        return msg;
    }

    //Экранная клавиатура, прикреплённая к /start
    private ReplyKeyboardMarkup addKeyboardToMsg() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow();
        for (String b : ConstAndComStorage.BUTTONS_ROW1) {
            row1.add(b);
        }
        /*
        KeyboardRow row2 = new KeyboardRow();
        for(String b: ConstAndComStorage.BUTTONS_ROW2){
            row2.add(b);
        }
         */
        List<KeyboardRow> kRows = new ArrayList<>(List.of(row1));

        keyboardMarkup.setKeyboard(kRows);
        return keyboardMarkup;
    }
}
