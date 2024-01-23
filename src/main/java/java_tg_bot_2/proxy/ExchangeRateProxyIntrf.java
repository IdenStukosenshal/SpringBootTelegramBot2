package java_tg_bot_2.proxy;

import java_tg_bot_2.dto.CurrencyPair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exchangerate", url = "${name.service.url}")
public interface ExchangeRateProxyIntrf {
    @GetMapping("/")
    CurrencyPair getCurrencyPair();
}
