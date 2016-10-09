package hackupc.skytrips.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hackupc.skytrips.R;
import hackupc.skytrips.helper.Helper;
import hackupc.skytrips.models.Place;
import hackupc.skytrips.models.Route;
import hackupc.skytrips.ui.PartnerPriceActivity;
import hackupc.skytrips.ui.view.CheapestFlightView;
import hackupc.skytrips.ui.view.CompoundTextView;

/**
 * Created by whdinata on 9/21/16.
 */
public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.ViewHolder> {

    private static final String TAG = PriceListAdapter.class.getName();
    private CompoundTextView.OnRemoveButtonClicked callback;
    private Route routes[] = new Route[0];

    public PriceListAdapter(){
        routes = Helper.routes;

        Log.d(TAG, "Routes: " + routes.length);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cheapest_flight, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Route route = routes[position];
        holder.textView.setDate(route.getTime());
        holder.textView.setTitle(route.getFrom() + " to " + route.getTo());
        holder.textView.setPrice(route.getPrice());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PartnerPriceActivity.createIntent(view.getContext(), route.getLink());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routes.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CheapestFlightView textView;

        public ViewHolder(View view){
            super(view);
            textView = (CheapestFlightView) view;
        }
    }
}
