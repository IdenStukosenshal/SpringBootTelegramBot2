package java_tg_bot_2.services;

import java_tg_bot_2.config.BotConfig;
import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
import java_tg_bot_2.models.ReminderMessage;
import java_tg_bot_2.repositories.ReminderMsgRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class MainService extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final CommandProcessing commandProcessing;
    private final CallBackProcessing callBackProcessing;
    private final ReminderMsgRepo reminderMsgRepo;

    @Autowired
    public MainService(BotConfig botConfig, CallBackProcessing callBackProcessing, CommandProcessing commandProcessing, ReminderMsgRepo reminderMsgRepo) {
        super(botConfig.getToken());
        this.botConfig = botConfig;
        this.commandProcessing = commandProcessing;
        this.callBackProcessing = callBackProcessing;
        this.reminderMsgRepo = reminderMsgRepo;

        setCommandList();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
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
                simpleSendMessage(new SendMessage(String.valueOf(chatId), ConstantsStorage.UNKNOWN_COM_TXT.getText()));
            }

        } else if (update.hasCallbackQuery()) {
            simpleSendMessage(callBackProcessing.processing(update));
        }
    }

    //Простой метод отправки уже готовых сообщений
    public void simpleSendMessage(SendMessage msg) {
        try {
            execute(msg);
        } catch (TelegramApiException ee) {
            log.error(ConstantsStorage.ERROR_TEXT.getText() + ee.getMessage());
        }
    }

    /*Метод осуществляет проверку списка сообщений через некоторый промежуток времени.
Если подошло время отправить - он это делает
    Если список будет очень большим ВОЗМОЖНО всё перестанет работать
 */
    @Scheduled(cron = "0 * * * * *") // 0 *...* каждую минуту
    //секунды, минуты, часы, дата(номер дня), месяц, день недели
    //* любое значение, 6 звёзд: каждую секунду, 0 0 *...: каждый час в 00:00
    private void checkToSend() {
        Iterable<ReminderMessage> allMsgList = reminderMsgRepo.findAll();
        LocalDateTime ldatetimeNow = LocalDateTime.now();

        for (ReminderMessage savedMsg : allMsgList) {
            LocalDateTime ldatetimeSAVed = savedMsg.getTimeToRemind();
            //Класс Duration для хранения длительности, промежутка
            //https://stackoverflow.com/questions/24491243/why-cant-i-get-a-duration-in-minutes-or-hours-in-java-time
            if (Duration.between(ldatetimeNow, ldatetimeSAVed).toMinutesPart() <= 2) { //Сообщение отправится примерно за 2 минуты до
                SendMessage message = new SendMessage();
                message.setChatId(savedMsg.getUserId());
                message.setText(savedMsg.getText() + "\nhas been saved: " + "\n" + savedMsg.getCreatedAt());
                simpleSendMessage(message); //отправка сообщения
                reminderMsgRepo.deleteById(savedMsg.getId()); //удаление сообщения из БД
            }
        }
    }

    private void setCommandList() {
        //меню списка команд, нельзя использовать верхний регистр
        List<BotCommand> listCommands = new ArrayList<>(Arrays.asList(
                new BotCommand(CommandsStorage.START.getText(), "Start working"),
                new BotCommand(CommandsStorage.DELETE_LAST_REMINDER.getText(), "delete last reminder_me message"),
                new BotCommand(CommandsStorage.HELP.getText(), "stop it, get some help")
        ));
        try {
            execute(new SetMyCommands(listCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException ee) {
            log.error("Error creating bot's command list: " + ee.getMessage());
        }
    }


}
