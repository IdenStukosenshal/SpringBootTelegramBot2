package java_tg_bot_2.proxy;

import java_tg_bot_2.dto.CurrencyPair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//OpenFeign создаст реализацию интерфейса в виде бина
@FeignClient(name = "exchangerate", url = "${name.service.url}")
public interface ExchangeRateProxyIntrf {
    @GetMapping("/BTC-USD/buy")
    CurrencyPair getCurrencyPair();
    // Запрос выглядит так: https://api.coinbase.com/v2/prices/BTC-USD/buy
    //Недостаток в том, что НЕИЗВЕСТНО КАК указать пару в параметре запроса
}
