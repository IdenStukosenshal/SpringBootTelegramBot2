package java_tg_bot_2.services;

import java_tg_bot_2.config.BotConfig;
import java_tg_bot_2.config.ConstAndComStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class MainService extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final CommandProcessing commandProcessing;
    private final CallBackProcessing callBackProcessing;

    @Autowired
    public MainService(BotConfig botConfig, CallBackProcessing callBackProcessing, CommandProcessing commandProcessing) {
        this.botConfig = botConfig;
        this.commandProcessing = commandProcessing;
        this.callBackProcessing = callBackProcessing;

        setCommandList();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    //deprecated method, но без него не работает
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String messageText;
        long chatId;

        if (update.hasMessage() && update.getMessage().hasText()) {
            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            if (messageText.startsWith("/")) {
                simpleSendMessage(commandProcessing.processing(update));
            } else {
                simpleSendMessage(new SendMessage(String.valueOf(chatId), ConstAndComStorage.UNKNOWN_COMMAND.getText()));
            }

        } else if (update.hasCallbackQuery()) {
            simpleSendMessage(callBackProcessing.processing(update));
        }
    }

    //Простой метод отправки уже готовых сообщений
    private void simpleSendMessage(SendMessage msg) {
        try {
            execute(msg);
        } catch (TelegramApiException ee) {
            log.error(ConstAndComStorage.ERROR_TEXT + ee.getMessage());
        }
    }

    private void setCommandList(){
        //меню списка команд, нельзя использовать верхний регистр
        List<BotCommand> listCommands = new ArrayList<>(Arrays.asList(
                new BotCommand(ConstAndComStorage.START.getText(), "Start working"),
                new BotCommand(ConstAndComStorage.HELP.getText(), "stop it, get some help")
        ));
        try{
            execute(new SetMyCommands(listCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException ee){
            log.error("Error creating bot's command list: " + ee.getMessage());
        }
    }
}
