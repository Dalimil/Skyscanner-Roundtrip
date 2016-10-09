package hackupc.skytrips;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hackupc.skytrips.adapter.DestinationListAdapter;
import hackupc.skytrips.adapter.LocationAutoCompleteAdapter;
import hackupc.skytrips.callbacks.AutoCompleteTextWatcher;
import hackupc.skytrips.callbacks.OnPlaceListReturned;
import hackupc.skytrips.connection.API;
import hackupc.skytrips.connection.Param;
import hackupc.skytrips.models.Place;
import hackupc.skytrips.ui.view.CompoundTextView;

public class MainActivity extends AppCompatActivity implements OnPlaceListReturned, CompoundTextView.OnRemoveButtonClicked{

    private static final String TAG = MainActivity.class.getName();
    private static final int SOURCE_RESULT = 0;
    private static final int DESTINATION_RESULT = 1;
    private LocationAutoCompleteAdapter sourceAdapter;
    private LocationAutoCompleteAdapter destinationAdapter;
    private AutoCompleteTextView tvSource;
    private AutoCompleteTextView tvDestination;
    private CompoundTextView tvChosenSource;
    private RecyclerView container;
    private List<Place> placesList = new ArrayList<>();
    private DestinationListAdapter destinationListAdapter;
    private static TextView calendar;
    private EditText etBudget;
    private String chosenSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API.init(this);

        destinationListAdapter = new DestinationListAdapter(this);

        calendar = (TextView) findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        etBudget = (EditText) findViewById(R.id.budget);

        container = (RecyclerView) findViewById(R.id.container);
        container.setAdapter(destinationListAdapter);

        tvChosenSource = (CompoundTextView) findViewById(R.id.chosen_source);

        sourceAdapter = new LocationAutoCompleteAdapter(this);
        destinationAdapter = new LocationAutoCompleteAdapter(this);

        API.init(this);

        tvSource = (AutoCompleteTextView) findViewById(R.id.source);
        tvDestination = (AutoCompleteTextView) findViewById(R.id.destination);
        tvDestination.setAdapter(destinationAdapter);
        tvSource.setAdapter(sourceAdapter);
        tvSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "POSITION: " + i);

                Place place = (Place) adapterView.getItemAtPosition(i);
                tvSource.setText("");

                tvChosenSource.setPlace(place);
                tvChosenSource.setVisibility(View.VISIBLE);
                chosenSource = place.getPlaceId();
            }
        });

        tvDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "POSITION: " + i);

                Place place = (Place) adapterView.getItemAtPosition(i);
                //tvDestination.setText(place.getPlaceName() + " (" + place.getPlaceId() + ")");
                tvDestination.setText("");
                placesList.add(place);
                destinationListAdapter.setPlaces(placesList);
            }
        });

        tvSource.addTextChangedListener(new AutoCompleteTextWatcher(this, this, SOURCE_RESULT));
        tvDestination.addTextChangedListener(new AutoCompleteTextWatcher(this, this, DESTINATION_RESULT));

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cities[] = new String[placesList.size() + 1];
                cities[0] = chosenSource;
                for(int i = 0; i < placesList.size(); i++){
                    cities[i + 1] = placesList.get(i).getPlaceId();
                }
                API.requestRoute(MainActivity.this, cities, calendar.getText().toString(), etBudget.getText().toString());
            }
        });
    }

    @Override
    public void onResponse(Place[] places, int resultId) {
        if(resultId == SOURCE_RESULT) {
            sourceAdapter.setPlaceData(places);
        } else{
            destinationAdapter.setPlaceData(places);
        }
    }

    @Override
    public void onButtonClicked(int idx) {
        placesList.remove(idx);
        Log.d(TAG, "Position: " + idx + " " + placesList.size());
        destinationListAdapter.setPlaces(placesList);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);

            try {
                java.lang.reflect.Field[] datePickerDialogFields = dialog.getClass().getDeclaredFields();
                for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                    if (datePickerDialogField.getName().equals("mDatePicker")) {
                        datePickerDialogField.setAccessible(true);
                        DatePicker datePicker = (DatePicker) datePickerDialogField.get(dialog);
                        java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                        for (java.lang.reflect.Field datePickerField : datePickerFields) {
                            Log.i("test", datePickerField.getName());
                            if ("mDaySpinner".equals(datePickerField.getName())) {
                                datePickerField.setAccessible(true);
                                Object dayPicker = datePickerField.get(datePicker);
                                ((View) dayPicker).setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {
            }
            // Create a new instance of DatePickerDialog and return it
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month++;
            calendar.setText(year + "-" + ((month < 10 ? "0" : "") + month));
        }
    }
}
