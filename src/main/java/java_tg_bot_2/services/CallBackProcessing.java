package java_tg_bot_2.services;

import java_tg_bot_2.services.callback.GetExchangeRates;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallBackProcessing {


    private final GetExchangeRates getExchangeRates;

    public CallBackProcessing(GetExchangeRates getExchangeRates) {
        this.getExchangeRates = getExchangeRates;
    }

    SendMessage processing(Update update){

        return getExchangeRates.respond(update);
    }
}
