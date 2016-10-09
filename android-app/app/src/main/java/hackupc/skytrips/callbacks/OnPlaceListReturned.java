package hackupc.skytrips.callbacks;

import hackupc.skytrips.models.Place;

/**
 * Created by whdinata on 10/8/16.
 */
public interface OnPlaceListReturned {
    void onResponse(Place[] places, int resultId);
}
