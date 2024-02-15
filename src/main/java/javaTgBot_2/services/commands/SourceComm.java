package javaTgBot_2.services.commands;

import javaTgBot_2.config.CommandsStorage;
import javaTgBot_2.config.ConstantsStorage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SourceComm implements CommandIntrf{
    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();
        return new SendMessage(String.valueOf(chatId), ConstantsStorage.REPO_TXT.getText());
    }

    @Override
    public CommandsStorage getCommandName() {
        return CommandsStorage.SOURCE_CODE;
    }
}
