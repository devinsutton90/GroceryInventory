package project.wgu.sutton.devin.groceryinventory.model;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import project.wgu.sutton.devin.groceryinventory.R;
import project.wgu.sutton.devin.groceryinventory.database.DataProvider;

public final class FoodAdapter extends ArrayAdapter<Food> {

    public FoodAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FoodAdapter(Context context, int resource, List<Food> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DataProvider dataProvider = new DataProvider(getContext());
        dataProvider.open();
        ArrayList<Location> locations = dataProvider.getLocations();

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_layout, null);
        }

        Food p = (Food) getItem(position);


        if (p != null) {
            TextView nameTextView = (TextView) v.findViewById(R.id.item_name_text);
            TextView quantityTextView = (TextView) v.findViewById(R.id.quantity_text);
            TextView dateTextView = (TextView) v.findViewById(R.id.date_text);
            TextView locationTextView = (TextView) v.findViewById(R.id.location_text_view);


            if (nameTextView != null) {
                nameTextView.setText(p.getName());
            }

            if (quantityTextView != null) {
                quantityTextView.setText(Double.toString(p.getQuantity()));
            }

            if (dateTextView != null) {
                dateTextView.setText(p.getExpiration());
            }

            if (locationTextView != null) {
                int locationId = p.getLocation();
                for (Location location: locations) {
                        if (location.getId() == locationId){
                            locationTextView.setText(location.getName());
                        }
                }
            }
            try {
                Calendar calendar = Calendar.getInstance();
                Date today = new Date();
                calendar.add(Calendar.DATE, 3);
                Date threeDays = calendar.getTime();
                System.out.println(p.getExpiration());
                Date date = new SimpleDateFormat("MM.dd.yyyy").parse(p.getExpiration());
                if(date.after(threeDays)){
                    v.setBackgroundColor(Color.GREEN);
                } else if (date.before(threeDays) && date.after(today)){
                    v.setBackgroundColor(Color.YELLOW);
                } else{
                    v.setBackgroundColor(Color.RED);
                }
            } catch (ParseException parseException){
                Log.i("Parse Error", "error parsing date");
            }
        }

        return v;
    }

}
