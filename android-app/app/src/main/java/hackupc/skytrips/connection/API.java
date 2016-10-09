package hackupc.skytrips.connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import hackupc.skytrips.callbacks.OnPlaceListReturned;
import hackupc.skytrips.helper.Helper;
import hackupc.skytrips.models.Place;
import hackupc.skytrips.models.Route;
import hackupc.skytrips.ui.PriceListActivity;

/**
 * Created by whdinata on 10/8/16.
 */
public final class API {
    private static final String TAG = API.class.getName();
    private static final String SKY_TRIP_API_URL = "http://skyroundtrip.localtunnel.me/trip";
    private static final String API_URL = "http://partners.api.skyscanner.net/apiservices/";
    private static final String API_KEY = "prtl6749387986743898559646983194";
    private static final String AUTOSUGGEST = API_URL + "autosuggest/v1.0/%1$s/%2$s/%3$s/?query=%4$s&apiKey=" + API_KEY;
    private static ProgressDialog dialog;

    public static void init(Context context){
        dialog = new ProgressDialog(context);
        dialog.setTitle("Fetching Data");
        dialog.setMessage("Please wait...");
    }

    public static void requestRoute(final Context context, String[] cities, String date, String budget){
        dialog.show();
        final JSONObject paramObject = new JSONObject();
        try {
            JSONArray cityArray = new JSONArray();
            for (int i = 0; i < cities.length; i++) {
                cityArray.put(cities[i]);
            }

            paramObject.put("cities", cityArray);
            paramObject.put("year-month", date);
            paramObject.put("budget", budget);
        } catch(JSONException e){
            e.printStackTrace();
        }

        Log.d(TAG, "Params: " + paramObject.toString());

        StringRequest request = new StringRequest(Request.Method.POST, SKY_TRIP_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    Route[] routes = new Route[array.length()];

                    Log.d(TAG, "array length: " + array.length());

                    for(int i = 0; i < routes.length; i++){
                        routes[i] = new Route(array.getJSONObject(i));
                    }

                    Helper.routes = routes;
                    Log.d(TAG, "Response: " + response);
                } catch (JSONException e){
                    e.printStackTrace();
                }

                dialog.dismiss();
                Intent intent = new Intent(context, PriceListActivity.class);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
                dialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("params", paramObject.toString());

                Log.d(TAG, "Params: " + paramObject.toString());

                return params;
            }
        };

        STRequestQueue.getInstance(context).addToRequestQueue(request);
    }

    public static void getListPlaces(Context context, Param param, final OnPlaceListReturned callback, final int resultId){
        //dialog.show();
        String url = String.format(AUTOSUGGEST, param.getMarket(), param.getCurrency(), param.getLocale(), param.getQuery());
        Log.d(TAG, "Endpoint: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Places");
                    Place[] places = new Place[jsonArray.length()];

                    for(int i = 0; i < places.length; i++){
                        places[i] = new Place(jsonArray.getJSONObject(i));
                    }

                    callback.onResponse(places, resultId);
                    Log.d(TAG, "Response: " + response);
                } catch (JSONException e){
                    e.printStackTrace();
                }

//                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
//                dialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };

        STRequestQueue.getInstance(context).addToRequestQueue(request);
    }
}
