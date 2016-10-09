package hackupc.skytrips.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import hackupc.skytrips.R;

/**
 * Created by whdinata on 10/8/16.
 */
public class CheapestFlightView extends LinearLayout {
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvPrice;

    public CheapestFlightView(Context context) {
        super(context);
        initView();
    }

    public CheapestFlightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        applyAttributes(attrs, 0);
    }

    public CheapestFlightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        applyAttributes(attrs, defStyleAttr);
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setDate(String date){
        tvDate.setText(date);
    }

    public void setPrice(String price){
        tvPrice.setText("£" + price);
    }


    private void applyAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CompoundTextView, defStyleAttr, 0);

        try {
            setPrice(a.getString(R.styleable.CheapestFlightView_price));
            setTitle(a.getString(R.styleable.CheapestFlightView_title));
            setDate(a.getString(R.styleable.CheapestFlightView_date));
        } finally {
            a.recycle();
        }
    }

    private void initView(){
        View view = inflate(getContext(), R.layout.cheapest_flight_view, this);
        tvPrice = (TextView) view.findViewById(R.id.price);
        tvTitle = (TextView) view.findViewById(R.id.title);
        tvDate = (TextView) view.findViewById(R.id.date);
    }
}
