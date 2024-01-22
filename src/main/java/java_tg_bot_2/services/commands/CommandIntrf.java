package java_tg_bot_2.services.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс, который должны реализовать все классы начальных команд
 * типа /start, /help и тд.
 */
public interface CommandIntrf {
    SendMessage respond(Update update);
}
