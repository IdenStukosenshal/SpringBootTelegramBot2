package java_tg_bot_2.dto;

import java.util.Map;

/*
    JSON:
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
public class CurrentAllPrices {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public record Data(String currency, Map<String, String> rates) {}
}
