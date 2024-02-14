package javaTgBot_2.services.commands;

import javaTgBot_2.config.CommandsStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс, который должны реализовать все классы начальных команд
 * типа /start, /help и тд.
 */
public interface CommandIntrf {
    SendMessage respond(Update update);

    CommandsStorage getCommandName();
}
