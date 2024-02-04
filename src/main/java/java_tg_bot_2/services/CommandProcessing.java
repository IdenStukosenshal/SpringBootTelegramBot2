package java_tg_bot_2.services;

import java_tg_bot_2.config.ConstantsStorage;
import java_tg_bot_2.services.commands.CommandIntrf;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandProcessing {
    //Обработка всех команд, начинающихся с "/"
    private final Map<String, CommandIntrf> commandDict;

    /*  Версия с использованием Stream  API
        public CommandProcessing(List<CommandIntrf> commands) {
            this.commandDict = commands.stream().collect(Collectors.toMap(CommandIntrf::getCommandName.getText(), Function.identity()));
        }
     */
    //автоматическое добавление классов, которые реализуют этот интерфейс,
    //Но каждая команда(класс) должна быть бином
    public CommandProcessing(List<CommandIntrf> commandsObj) {
        Map<String, CommandIntrf> tmpDict = new HashMap<>();
        for (CommandIntrf commObj : commandsObj) {
            tmpDict.put(commObj.getCommandName().getText(), commObj);
        }
        this.commandDict = tmpDict;
    }


    //на каждую команду дать свой ответ
    SendMessage processing(Update update) {

        String msgCommand = update.getMessage().getText().split(" ")[0]; //отделить команду от возможных лишних слов
        long chatId = update.getMessage().getChatId();

        CommandIntrf commandIntrf = commandDict.get(msgCommand); //объект, обрабатывающий команду
        if (commandIntrf != null) return commandIntrf.respond(update); //вызвать метод обработки команды
        else return new SendMessage(String.valueOf(chatId), ConstantsStorage.UNKNOWN_COM_TXT.getText());
    }
}