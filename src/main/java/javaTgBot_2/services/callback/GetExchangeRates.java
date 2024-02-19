package javaTgBot_2.services.callback;

import javaTgBot_2.config.ConstantsStorage;
import javaTgBot_2.proxy.AllRateProxyIntrf;
import javaTgBot_2.proxy.ExchangeRateProxyIntrf;
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
        String buyAmount = currencyPairBuy.getData().amount();
        String buyResponse = "buy: " + baseCoinCode + "/" + toCoinCode + " : " + buyAmount;

        var currencyPairSell = exchangeRateProxyIntrf.getCurrencyPair(baseCoinCode, toCoinCode, "sell");
        String sellAmount = currencyPairSell.getData().amount();
        String sellResponse = "sell: " + baseCoinCode + "/" + toCoinCode + " : " + sellAmount;

        var allPricesVal = allRateProxyIntrf.getAllPrices(baseCoinCode).getData(); //record
        String allAmount = allPricesVal.rates().get(toCoinCode);
        String Allresponse = baseCoinCode + "/" + toCoinCode + ": " + allAmount;

        return new SendMessage(String.valueOf(chatId), buyResponse + "\n" + sellResponse + "\n" + Allresponse);

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
