package hackupc.skytrips.callbacks;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import hackupc.skytrips.connection.API;
import hackupc.skytrips.connection.Param;

/**
 * Created by whdinata on 10/8/16.
 */
public class AutoCompleteTextWatcher implements TextWatcher {

    private static final String TAG = AutoCompleteTextWatcher.class.getName();
    private Context context;
    private OnPlaceListReturned callback;
    private int resultId;

    public AutoCompleteTextWatcher(Context context, OnPlaceListReturned callback, int resultId){
        this.context = context;
        this.callback = callback;
        this.resultId = resultId;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d(TAG, "before text changed");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d(TAG, "on text changed");
        Param param = new Param("GB", "GBP", "en-GB", charSequence.toString());
        API.getListPlaces(context, param, callback, resultId);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
