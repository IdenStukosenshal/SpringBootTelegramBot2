package javaTgBot_2.proxy;

import javaTgBot_2.dto.CurrencyPair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//OpenFeign создаст реализацию интерфейса в виде бина
@FeignClient(name = "exchangerate", url = "${name.service.url}")
public interface ExchangeRateProxyIntrf {

    // Запрос выглядит так: https://api.coinbase.com/v2/prices/BTC-USD/buy
    //                      https://api.coinbase.com/v2/prices/BTC-USD/sell
    @GetMapping("/{base}-{currency}/{buyOrSell}")
    CurrencyPair getCurrencyPair(@PathVariable(value = "base") String base,
                                 @PathVariable(value = "currency") String currency,
                                 @PathVariable(value = "buyOrSell") String buyOrSell);

}

