package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
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
        SendMessage msg = new SendMessage(String.valueOf(chatId), ConstantsStorage.START_TXT.getText());
        msg.setReplyMarkup(addKeyboardToMsg()); //добавление клавиатуры
        return msg;
    }

    //Экранная клавиатура, прикреплённая к /start
    private ReplyKeyboardMarkup addKeyboardToMsg() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow();
        for (CommandsStorage b : CommandsStorage.BUTTONS_ROW1) {
            row1.add(b.getText());
        }
        List<KeyboardRow> kRows = new ArrayList<>(List.of(row1));

        keyboardMarkup.setKeyboard(kRows);
        return keyboardMarkup;
    }

    @Override
    public String getCommandName(){
        return CommandsStorage.START.getText();
    }
}
