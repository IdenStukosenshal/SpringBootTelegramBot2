package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.ButtonsExchangeStorage;
import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExchangeRate implements CommandIntrf {

    private final InlineKeyboardMarkup inlineKeyboardMarkup = keyBoardCreating();

    @Override
    public SendMessage respond(Update update) {
        //Вывод сообщения с клавиатурой выбора монеты
        long chatId = update.getMessage().getChatId();
        SendMessage choiceValMessage = new SendMessage(String.valueOf(chatId), ConstantsStorage.CHOICE_TXT.getText());
        choiceValMessage.setReplyMarkup(inlineKeyboardMarkup);
        return choiceValMessage;
    }

    public CommandsStorage getCommandName() {
        return CommandsStorage.EXCHANGE_RATES;
    }


    private InlineKeyboardMarkup keyBoardCreating() {
        //Метод создаёт и возвращает клавиатуру, которая прикрепляется к сообщению
        //Кнопки содержат CallbackData (имя кнопки)
        int nButtons = 3; //количество кнопок на строку
        ButtonsExchangeStorage[] buttonsMassive = ButtonsExchangeStorage.values(); //список объектов enum кнопок
        InlineKeyboardMarkup keyboardMrkp = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        //paginacia
        int nRows = buttonsMassive.length / nButtons + 1; //количество строк кнопок
        int index = -1;
        for (int i = 0; i < nRows; i++) {
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            for (int j = 0; j < nButtons; j++) {
                index++;
                if (index == buttonsMassive.length) break;
                var button = new InlineKeyboardButton(buttonsMassive[index].getName());
                button.setCallbackData(buttonsMassive[index].getName());
                row1.add(button);
            }
            rows.add(row1);
        }
        keyboardMrkp.setKeyboard(rows);
        return keyboardMrkp;
    }
}
