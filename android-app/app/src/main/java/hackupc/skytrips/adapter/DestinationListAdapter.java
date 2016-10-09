package hackupc.skytrips.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hackupc.skytrips.R;
import hackupc.skytrips.models.Place;
import hackupc.skytrips.ui.view.CompoundTextView;

/**
 * Created by whdinata on 9/21/16.
 */
public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.ViewHolder> {

    private static final String TAG = DestinationListAdapter.class.getName();
    private CompoundTextView.OnRemoveButtonClicked callback;
    private Place places[] = new Place[0];

    public DestinationListAdapter(CompoundTextView.OnRemoveButtonClicked callback){
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chosen_city, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Place place = places[position];
        holder.textView.setPlace(place);
        holder.textView.setRemoveButtonTag(position);
        holder.textView.setOnRemoveButtonClicked(callback);
        holder.textView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return places.length;
    }

    public void setPlaces(List<Place> placeList){
        places = new Place[placeList.size()];
        for(int i = 0; i < placeList.size(); i++) {
            places[i] = placeList.get(i);
        }

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CompoundTextView textView;

        public ViewHolder(View view){
            super(view);
            textView = (CompoundTextView) view.findViewById(R.id.chosen_source);
        }
    }
}
