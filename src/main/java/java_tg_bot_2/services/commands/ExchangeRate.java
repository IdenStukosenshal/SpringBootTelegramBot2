package java_tg_bot_2.services.commands;

import java_tg_bot_2.dto.CurrentAllPrices;
import java_tg_bot_2.proxy.AllRateProxyIntrf;
import java_tg_bot_2.proxy.ExchangeRateProxyIntrf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ExchangeRate implements CommandIntrf {

    private final ExchangeRateProxyIntrf exchangeRateProxyIntrf;
    private final AllRateProxyIntrf allRateProxyIntrf;

    @Autowired
    public ExchangeRate(ExchangeRateProxyIntrf exchangeRateProxyIntrf,
                        AllRateProxyIntrf allRateProxyIntrf) {
        this.exchangeRateProxyIntrf = exchangeRateProxyIntrf;
        this.allRateProxyIntrf = allRateProxyIntrf;
    }

    @Override
    public SendMessage respond(Update update) {
        long chatId = update.getMessage().getChatId();

        var dataMap = exchangeRateProxyIntrf.getCurrencyPair().getData();
        String amount = dataMap.get("amount");
        String base = dataMap.get("base");
        String currency = dataMap.get("currency");

        String response1 = base + "/" + currency + " : " + amount;

        CurrentAllPrices.Data data2 = allRateProxyIntrf.getAllPrices("BTC").getData();
        String amount2 = data2.getRates().get("USD");
        String response2 = "BTC" + "/" + "USD" + ": " + amount2;

        return new SendMessage(String.valueOf(chatId), response1 + "\n" + response2);

    }
}
/*
{
    "data": {
        "amount": "39757.925",
        "base": "BTC",
        "currency": "USD"
    }
}
*/