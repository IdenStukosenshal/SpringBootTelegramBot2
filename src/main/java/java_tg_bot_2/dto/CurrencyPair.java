package java_tg_bot_2.dto;

import java.util.Map;

/*
https://docs.cloud.coinbase.com/sign-in-with-coinbase/docs/api-prices

    JSON:     GET https://api.coinbase.com/v2/prices/BTC-USD/buy   https://api.coinbase.com/v2/prices/BTC-USD/sell
{
    "data": {
        "amount": "39757.925",
        "base": "BTC",
        "currency": "USD"
    }
}
 */
public class CurrencyPair {
    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
