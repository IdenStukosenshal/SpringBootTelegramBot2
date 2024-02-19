package javaTgBot_2.services.commands;

import javaTgBot_2.config.CommandsStorage;
import javaTgBot_2.config.ConstantsStorage;
import javaTgBot_2.repositories.ReminderMsgRepo;
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
        if (reminderMsgRepo.findFirstByUserIdOrderByCreatedAtDesc(chatId).isPresent()) { //можно было применить .orElseGet()
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
    public CommandsStorage getCommandName() {
        return CommandsStorage.DELETE_LAST_REMINDER;
    }
}
