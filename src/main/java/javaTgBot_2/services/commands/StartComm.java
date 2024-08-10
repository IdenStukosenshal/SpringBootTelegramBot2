package javaTgBot_2.services.commands;

import javaTgBot_2.config.CommandsStorage;
import javaTgBot_2.config.ConstantsStorage;
import javaTgBot_2.models.UserT;
import javaTgBot_2.repositories.UserRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
public class StartComm implements CommandIntrf {

    private final UserRepo userRepo;

    public StartComm(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();
        SendMessage msg = new SendMessage(String.valueOf(chatId), ConstantsStorage.START_TXT.getText());
        msg.setReplyMarkup(addKeyboardToMsg()); //добавление клавиатуры
        registerUser(update); //регистрация пользователя
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
    public CommandsStorage getCommandName() {
        return CommandsStorage.START;
    }

    //Создание записи в таблице для пользователя, если её нет, пока этот функционал не используется
    public void registerUser(Update update) {
        var message = update.getMessage();
        Long chatId = message.getChatId();
        if (!userRepo.existsById(chatId)) { //если записи не существует
            String firstname = message.getChat().getFirstName();
            String username = message.getChat().getUserName();
            userRepo.saveUser(chatId, firstname, username);
            /*
            Метод userRepo.save(userT) существует, но вызывает ошибку
            Failed to update entity, Id not found in database
            */
        }
    }
}
