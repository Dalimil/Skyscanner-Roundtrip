package hackupc.skytrips.connection;

/**
 * Created by whdinata on 10/8/16.
 */
public class Param {
    private String market;
    private String currency;
    private String locale;
    private String query;

    public Param(String market, String currency, String locale, String query) {
        this.market = market;
        this.currency = currency;
        this.locale = locale;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
