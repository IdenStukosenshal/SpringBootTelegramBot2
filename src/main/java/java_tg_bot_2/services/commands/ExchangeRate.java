package java_tg_bot_2.services.commands;

import java_tg_bot_2.dto.CurrencyPair;
import java_tg_bot_2.proxy.ExchangeRateProxyIntrf;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ExchangeRate implements CommandIntrf{

    private final ExchangeRateProxyIntrf exchangeRateProxyIntrf;

    public ExchangeRate(ExchangeRateProxyIntrf exchangeRateProxyIntrf) {
        this.exchangeRateProxyIntrf = exchangeRateProxyIntrf;
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();

        CurrencyPair currencyPair = exchangeRateProxyIntrf.getCurrencyPair();

        CurrencyPair.Data data = currencyPair.getData();
        String response = data.getBase() + " / " + data.getCurrency() + " : " + data.getAmount();
        return new SendMessage(String.valueOf(chatId), response);
    }
}
