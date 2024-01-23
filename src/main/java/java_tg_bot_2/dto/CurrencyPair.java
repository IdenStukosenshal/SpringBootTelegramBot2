package java_tg_bot_2.dto;
/*
https://docs.cloud.coinbase.com/sign-in-with-coinbase/docs/api-exchange-rates
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

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        private String amount;
        private String base;
        private String currency;

        public String getAmount() {
            return amount;
        }
        public void setAmount(String amount) {
            this.amount = amount;
        }
        public String getBase() {
            return base;
        }
        public void setBase(String base) {
            this.base = base;
        }
        public String getCurrency() {
            return currency;
        }
        public void setCurrency(String currency) {
            this.currency = currency;
        }

    }
}
