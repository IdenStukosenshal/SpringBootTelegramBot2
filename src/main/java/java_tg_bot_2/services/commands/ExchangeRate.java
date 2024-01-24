package java_tg_bot_2.services.commands;

import java_tg_bot_2.config.CommandsStorage;
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

        var dataObj_1 = exchangeRateProxyIntrf.getCurrencyPair("BTC", "USD", "buy");
        String amount = dataObj_1.getData().amount();
        String base = dataObj_1.getData().base();
        String currency = dataObj_1.getData().currency();
        String response1 = "buy: " + base + "/" + currency + " : " + amount;

        var dataObj_1_5 = exchangeRateProxyIntrf.getCurrencyPair("BTC", "USD", "sell");
        String response1_5 = "sell: " + dataObj_1_5.getData().base() + "/" + dataObj_1_5.getData().currency() + " : " + dataObj_1_5.getData().amount();

        var dataObj_2 = allRateProxyIntrf.getAllPrices("BTC").getData();
        String amount2 = dataObj_2.rates().get("USD");
        String currency2 = dataObj_2.currency();
        String response2 = currency2 + "/" + "USD" + ": " + amount2;

        return new SendMessage(String.valueOf(chatId), response1 + "\n" + response1_5 + "\n" + response2);

    }
    public String getCommandName(){
        return CommandsStorage.ExchangeRate.getText();
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
/*
{
    "data": {
        "currency": "BTC",
        "rates": {
            "00": "575336.7571533382241308",
            ...
            ...
            "USD": "39209.2",
            "USDC": "39209.2"
        }
    }
}
 */