package javaTgBot_2.dto;

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
    private Data data;
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }

    public record Data(String base, String currency, String amount) {
    }
}
