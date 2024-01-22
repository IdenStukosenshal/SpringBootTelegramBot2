package java_tg_bot_2.services;

import java_tg_bot_2.config.ConstAndComStorage;
import java_tg_bot_2.services.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandProcessing {
    //Обработка всех команд, начинающихся с "/"

    private final Map<String, CommandIntrf> commandDict;

    public CommandProcessing() {
        this.commandDict = Map.of(
                ConstAndComStorage.START, new StartComm(),
                ConstAndComStorage.HELP, new HelpComm(),
                ConstAndComStorage.ExchangeRate, new ExchangeRate(),
                ConstAndComStorage.GetRandJoke, new GetRandomJoke()
        );
    }

    //на каждую команду дать свой ответ
    SendMessage processing(Update update){

        String msgCommand = update.getMessage().getText().split(" ")[0]; //отделить команду от возможных лишних слов
        long chatId = update.getMessage().getChatId();

        CommandIntrf commandIntrf = commandDict.get(msgCommand); //объект, обрабатывающий команду
        if (commandIntrf != null) return commandIntrf.respond(update); //вызвать метод обработки команды
        else return new SendMessage(String.valueOf(chatId), ConstAndComStorage.UNKNOWN_COMMAND);
    }
}
