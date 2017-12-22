package project.wgu.sutton.devin.groceryinventory.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import project.wgu.sutton.devin.groceryinventory.R;
import project.wgu.sutton.devin.groceryinventory.database.DataProvider;
import project.wgu.sutton.devin.groceryinventory.model.Food;

public class WasteReportActivity extends AppCompatActivity {

    DataProvider dataProvider;
    TextView wasted;
    ArrayList<Food> wastedFoodList;
    Double wastedTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_report);

        dataProvider = new DataProvider(this);
        dataProvider.open();
        wastedFoodList = dataProvider.getWastedFood();
        wastedTotal = 0.0;

        for (Food f: wastedFoodList){
            wastedTotal += f.getPrice();
        }
        wasted = findViewById(R.id.wastedTextView);
        wasted.setText("$" + Double.toString(wastedTotal));
    }


}
