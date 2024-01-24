package java_tg_bot_2.services;

import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
import java_tg_bot_2.services.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CommandProcessing {
    //Обработка всех команд, начинающихся с "/"

    private final Map<String, CommandIntrf> commandDict;

    public CommandProcessing(@Autowired ExchangeRate exchangeRate) {
        this.commandDict = Map.of(
                CommandsStorage.START.getText(), new StartComm(),
                CommandsStorage.HELP.getText(), new HelpComm(),
                CommandsStorage.ExchangeRate.getText(), exchangeRate,
                CommandsStorage.GetRandJoke.getText(), new GetRandomJoke()
        );
    }

/*  Инопланетные технологии, каждый класс команд нужно сделать бином, тогда это будет работать
    public CommandProcessing(List<CommandIntrf> commands) {
        this.commandDict = commands.stream().collect(Collectors.toMap(CommandIntrf::getCommandName, Function.identity()));
    }

 */


    //на каждую команду дать свой ответ
    SendMessage processing(Update update){

        String msgCommand = update.getMessage().getText().split(" ")[0]; //отделить команду от возможных лишних слов
        long chatId = update.getMessage().getChatId();

        CommandIntrf commandIntrf = commandDict.get(msgCommand); //объект, обрабатывающий команду
        if (commandIntrf != null) return commandIntrf.respond(update); //вызвать метод обработки команды
        else return new SendMessage(String.valueOf(chatId), ConstantsStorage.UNKNOWN_COM_TXT.getText());
    }
}
