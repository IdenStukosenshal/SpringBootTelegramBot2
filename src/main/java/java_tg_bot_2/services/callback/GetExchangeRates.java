package java_tg_bot_2.services.callback;

import java_tg_bot_2.config.ConstantsStorage;
import java_tg_bot_2.proxy.AllRateProxyIntrf;
import java_tg_bot_2.proxy.ExchangeRateProxyIntrf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GetExchangeRates {
    private final ExchangeRateProxyIntrf exchangeRateProxyIntrf;
    private final AllRateProxyIntrf allRateProxyIntrf;

    @Autowired
    public GetExchangeRates(ExchangeRateProxyIntrf exchangeRateProxyIntrf, AllRateProxyIntrf allRateProxyIntrf) {
        this.exchangeRateProxyIntrf = exchangeRateProxyIntrf;
        this.allRateProxyIntrf = allRateProxyIntrf;
    }

    public SendMessage respond(Update update) {
        String baseCoinCode = update.getCallbackQuery().getData();
        String toCoinCode = ConstantsStorage.COIN_CODE_BASE.getText();

        long chatId = update.getCallbackQuery().getMessage().getChatId();

        var currencyPairBuy = exchangeRateProxyIntrf.getCurrencyPair(baseCoinCode, toCoinCode, "buy");
        String amount = currencyPairBuy.getData().amount();
        String response1 = "buy: " + baseCoinCode + "/" + toCoinCode + " : " + amount;

        var currencyPairSell = exchangeRateProxyIntrf.getCurrencyPair(baseCoinCode, toCoinCode, "sell");
        String amount_1_5 = currencyPairSell.getData().amount();
        String response1_5 = "sell: " + baseCoinCode + "/" + toCoinCode + " : " + amount_1_5;

        var allPricesVal = allRateProxyIntrf.getAllPrices(baseCoinCode).getData();
        String amount2 = allPricesVal.rates().get(toCoinCode);
        String response2 = baseCoinCode + "/" + toCoinCode + ": " + amount2;

        return new SendMessage(String.valueOf(chatId), response1 + "\n" + response1_5 + "\n" + response2);

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
{
    "data": {
        "currency": "BTC",
        "rates": {
            "00": "575336.7571533382241308",
            ...
            450 строк
            ...
            "USD": "39209.2",
            "USDC": "39209.2"
        }
    }
}
 */
