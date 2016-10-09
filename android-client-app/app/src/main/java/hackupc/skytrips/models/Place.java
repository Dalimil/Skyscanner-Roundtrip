package hackupc.skytrips.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by whdinata on 10/8/16.
 */
public class Place {
    private String placeId;
    private String placeName;
    private String countryId;
    private String regionId;
    private String cityId;
    private String countryName;

    public Place(JSONObject jsonObject){
        try {
            placeId = jsonObject.getString("PlaceId").split("-")[0];
            placeName = jsonObject.getString("PlaceName");
            countryId = jsonObject.getString("CountryId");
            regionId = jsonObject.getString("RegionId");
            cityId = jsonObject.getString("CityId");
            countryName = jsonObject.getString("CountryName");
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object obj) {
        return placeId.equals(((Place)obj).getPlaceId());
    }
}
