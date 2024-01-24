package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpComm implements CommandIntrf{
    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();
        return new SendMessage(String.valueOf(chatId), ConstantsStorage.HELP_TXT.getText());
    }
    @Override
    public String getCommandName(){
        return CommandsStorage.HELP.getText();
    }
}
