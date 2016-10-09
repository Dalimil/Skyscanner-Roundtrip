package hackupc.skytrips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hackupc.skytrips.R;
import hackupc.skytrips.models.Place;

/**
 * Created by whdinata on 9/22/16.
 */
public class LocationAutoCompleteAdapter extends ArrayAdapter<Place> {

    private static final String TAG = LocationAutoCompleteAdapter.class.getName();
    private Place places[];

    public LocationAutoCompleteAdapter(Context context){
        super(context, 0, new ArrayList<Place>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.location_suggestion_item, null);
        }

        TextView tvCity = (TextView) view.findViewById(R.id.city);
        TextView tvAdditionalInfo = (TextView) view.findViewById(R.id.additional_info);

        tvCity.setText(places[position].getPlaceName() + " (" + places[position].getPlaceId() + ")");
        tvAdditionalInfo.setText(places[position].getCountryName());

        return view;
    }

    @Override
    public Place getItem(int position) {
        return places[position];
    }

    @Override
    public int getCount() {
        return places.length;
    }

    public void setPlaceData(Place places[]){
        this.places = places;
        addAll(places);
        notifyDataSetChanged();
    }
}
