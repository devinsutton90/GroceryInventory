package project.wgu.sutton.devin.groceryinventory.activites;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowId;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import project.wgu.sutton.devin.groceryinventory.R;
import project.wgu.sutton.devin.groceryinventory.database.DataProvider;
import project.wgu.sutton.devin.groceryinventory.model.Food;
import project.wgu.sutton.devin.groceryinventory.model.FoodAdapter;

public class MainActivity extends AppCompatActivity {
    DataProvider dataProvider;
    ListView listView;
    ArrayList<Food> list;
    FoodAdapter foodAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_food:
                Intent intentNewFood = new Intent(MainActivity.this, FoodDetailActivity.class);
                intentNewFood.putExtra("SOURCE", FoodDetailActivity.NEW_SOURCE_CODE);
                startActivity(intentNewFood);
                return true;
            case R.id.view_locations:
                Intent intentNewLocation = new Intent(MainActivity.this, LocationsActivity.class);
                startActivity(intentNewLocation);
                return true;
            case R.id.sort_location:
                ListView listView = findViewById(R.id.list_view);
                ArrayList<Food> list = dataProvider.getFoodByLocation();
                FoodAdapter foodAdapter = new FoodAdapter(this, R.layout.item_layout, list);
                listView.setAdapter(foodAdapter);
                return true;
            case R.id.sort_type:
                listView = findViewById(R.id.list_view);
                list = dataProvider.getFoodByName();
                foodAdapter = new FoodAdapter(this, R.layout.item_layout, list);
                listView.setAdapter(foodAdapter);
                return true;
            case R.id.sort_date:
                listView = findViewById(R.id.list_view);
                list = dataProvider.getFoodByExpiration();
                foodAdapter = new FoodAdapter(this, R.layout.item_layout, list);
                listView.setAdapter(foodAdapter);
                return true;
            case R.id.waste_report:
                Intent wasteIntent = new Intent(MainActivity.this, WasteReportActivity.class);
                startActivity(wasteIntent);
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataProvider = new DataProvider(this);
        dataProvider.open();


        createThreeDayAlert();

        listView = findViewById(R.id.list_view);
        list = dataProvider.getFoodByName();
        foodAdapter = new FoodAdapter(this, R.layout.item_layout, list);
        listView.setAdapter(foodAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food selected = (Food) adapterView.getItemAtPosition(i);
                Intent intent;
                intent = new Intent(MainActivity.this, FoodDetailActivity.class);
                intent.putExtra("SOURCE", FoodDetailActivity.DETAIL_SOURCE_CODE);
                intent.putExtra("FOOD_ID", selected.getId());
                startActivity(intent);
            }
        });
    }

    private void createThreeDayAlert(){
        DateFormat format = new SimpleDateFormat("MM.dd.yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 3);
        Date calendarDate = c.getTime();
        String threeDays = format.format(calendarDate);

        if (dataProvider.foodExpiringSoonCheck(threeDays)){
            NotificationManager notification=(NotificationManager)getSystemService(MainActivity.NOTIFICATION_SERVICE);
            Notification notify=new Notification.Builder
                    (getApplicationContext()).setContentTitle("Food nearing Expiration").setContentText("You have food expiring soon!").
                    setContentTitle("Expiring Fiid").build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.notify(0, notify);
        }
    }
}
