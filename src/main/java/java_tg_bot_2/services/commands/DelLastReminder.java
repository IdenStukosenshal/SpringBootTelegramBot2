package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.CommandsStorage;
import java_tg_bot_2.config.ConstantsStorage;
import java_tg_bot_2.repositories.ReminderMsgRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DelLastReminder implements CommandIntrf {
    private final ReminderMsgRepo reminderMsgRepo;

    public DelLastReminder(ReminderMsgRepo reminderMsgRepo) {
        this.reminderMsgRepo = reminderMsgRepo;
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();
        if (reminderMsgRepo.findFirstByUserIdOrderByCreatedAtDesc(chatId).isPresent()) {
            var savedMsg = reminderMsgRepo.findFirstByUserIdOrderByCreatedAtDesc(chatId).get();
            String text = "Message with:\n" + savedMsg.getText()
                    + "\ndate created: " + savedMsg.getCreatedAt()
                    + "\nsent date: " + savedMsg.getTimeToRemind()
                    + "\nwas deleted";
            reminderMsgRepo.deleteById(savedMsg.getId());
            return new SendMessage(String.valueOf(chatId), text);
        } else return new SendMessage(String.valueOf(chatId), ConstantsStorage.EMPTY_REMINDER_LIST.getText());
    }

    @Override
    public String getCommandName() {
        return CommandsStorage.DELETE_LAST_REMINDER.getText();
    }
}
