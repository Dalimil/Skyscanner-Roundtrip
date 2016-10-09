package hackupc.skytrips.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by whdinata on 10/8/16.
 */
public class Route {
    private String link;
    private String to;
    private String price;
    private String from;
    private String time;

    public Route(JSONObject jsonObject){
        try{
            link = jsonObject.getString("link");
            to = jsonObject.getString("to");
            price = jsonObject.getString("price");
            from = jsonObject.getString("from");
            time = jsonObject.getString("time");
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
