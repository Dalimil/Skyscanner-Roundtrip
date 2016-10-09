package hackupc.skytrips.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import hackupc.skytrips.R;
import hackupc.skytrips.models.Place;

/**
 * Created by whdinata on 10/8/16.
 */
public class CompoundTextView extends RelativeLayout {

    private ImageView ivRemove;
    private ImageView ivCity;
    private TextView tvCity;
    private TextView tvCountry;
    private OnRemoveButtonClicked callback;

    private static final Map<String, Integer> imageMap = new HashMap<String, Integer>(){{
            put("LOND", R.drawable.lond);
            put("PARI", R.drawable.pari);
            put("BUD", R.drawable.bud);
            put("AMS", R.drawable.ams);
            put("PRG", R.drawable.prg);
            put("BERL", R.drawable.berl);
            put("BCN", R.drawable.bcn);
            put("ROME", R.drawable.rome);
            put("LIS", R.drawable.lis);
            put("VIE", R.drawable.vie);
            put("CPH", R.drawable.cph);
            put("FLR", R.drawable.flr);
            put("VENI", R.drawable.veni);
            put("EDI", R.drawable.edi);
            put("DUB", R.drawable.dub);
            put("ZRH", R.drawable.zrh);
        }
    };

    public CompoundTextView(Context context) {
        super(context);
        initView();
    }

    public CompoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        applyAttributes(attrs, 0);
    }

    public CompoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        applyAttributes(attrs, defStyleAttr);
    }

    public void setPlace(Place place){
        setTextCity(place.getPlaceName() + " (" + place.getPlaceId() + ")");
        setTextCountry(place.getCountryName());
        setDrawableCity(imageMap.get(place.getPlaceId()));
    }

    public void setOnRemoveButtonClicked(OnRemoveButtonClicked callback){
        this.callback = callback;
    }

    public void setRemoveButtonTag(int position){
        ivRemove.setTag(position);
    }

    public void setDrawableWidth(int width) {
        if (width > 0) {
            ViewGroup.LayoutParams params = ivCity.getLayoutParams();
            params.width = width;
            ivCity.setLayoutParams(params);
        }
    }

    public void setDrawableHeight(int height) {
        if (height > 0) {
            ViewGroup.LayoutParams params = ivCity.getLayoutParams();
            params.height = height;
            ivCity.setLayoutParams(params);
        }
    }

    public void setDrawableCity(@DrawableRes int drawableResourceId) {
        if (drawableResourceId != 0) {
            ivCity.setImageResource(drawableResourceId);
        }
    }

    public void setDrawableRemove(@DrawableRes int drawableResourceId) {
        if (drawableResourceId != 0) {
            ivRemove.setImageResource(drawableResourceId);
        }
    }

    public void setTextCity(@StringRes int stringResourceId) {
        if (stringResourceId != 0) {
            tvCity.setText(stringResourceId);
        }
    }

    public void setTextCity(String city){
        tvCity.setText(city);
    }

    public void setTextCountry(@StringRes int stringResourceId) {
        if (stringResourceId != 0) {
            tvCountry.setText(stringResourceId);
        }
    }

    public void setTextCountry(String country){
        tvCountry.setText(country);
    }

    private void applyAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CompoundTextView, defStyleAttr, 0);

        try {
            setDrawableCity(a.getResourceId(R.styleable.CompoundTextView_drawable_city, 0));
            setDrawableRemove(a.getResourceId(R.styleable.CompoundTextView_drawable_remove, 0));
            setTextCity(a.getResourceId(R.styleable.CompoundTextView_text_city, 0));
            setTextCountry(a.getResourceId(R.styleable.CompoundTextView_text_country, 0));
            setDrawableWidth(a.getDimensionPixelSize(R.styleable.CompoundTextView_drawable_width, 0));
            setDrawableHeight(a.getDimensionPixelSize(R.styleable.CompoundTextView_drawable_height, 0));
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.compound_text_view, this);
        ivCity = (ImageView) view.findViewById(R.id.city_image);
        ivRemove = (ImageView) view.findViewById(R.id.remove);
        tvCity = (TextView) view.findViewById(R.id.city_name);
        tvCountry = (TextView) view.findViewById(R.id.country_name);

        ivRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null){
                    callback.onButtonClicked((int)view.getTag());
                } else{
                    setVisibility(View.GONE);
                }
            }
        });
    }

    public interface OnRemoveButtonClicked{
        void onButtonClicked(int index);
    }
}