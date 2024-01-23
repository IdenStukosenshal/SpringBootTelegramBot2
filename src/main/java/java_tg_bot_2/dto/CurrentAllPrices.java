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

    public static class Data {

        private String currency;
        private Map<String, String> rates;


        public String getCurrency() {
            return currency;
        }
        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Map<String, String> getRates() {
            return rates;
        }
        public void setRates(Map<String, String> rates) {
            this.rates = rates;
        }
    }
}
