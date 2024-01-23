package java_tg_bot_2.proxy;


import java_tg_bot_2.dto.CurrentAllPrices;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//OpenFeign создаст реализацию интерфейса в виде бина
@FeignClient(name = "allrate", url = "${name.serviceall.url}")
public interface AllRateProxyIntrf {
    @GetMapping()//Запрос выглядит так: https://api.coinbase.com/v2/exchange-rates?currency=BTC
    CurrentAllPrices getAllPrices(@RequestParam String currency);
}
