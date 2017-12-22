package project.wgu.sutton.devin.groceryinventory.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import project.wgu.sutton.devin.groceryinventory.R;
import project.wgu.sutton.devin.groceryinventory.database.DataProvider;
import project.wgu.sutton.devin.groceryinventory.model.Food;
import project.wgu.sutton.devin.groceryinventory.model.FoodAdapter;
import project.wgu.sutton.devin.groceryinventory.model.Location;

public class LocationsActivity extends AppCompatActivity {
    DataProvider dataProvider;
    ArrayList<Location> locations;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.locations_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_location:
                Intent intent = new Intent(LocationsActivity.this, LocationDetailActivity.class);
                intent.putExtra("SOURCE", LocationDetailActivity.NEW_SOURCE_CODE);
                startActivity(intent);
                return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        dataProvider = new DataProvider(this);
        dataProvider.open();
        locations = dataProvider.getLocations();

        ListView locationsListView = findViewById(R.id.locations_list_view);
        ArrayAdapter<Location> adapter = new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, locations);
        locationsListView.setAdapter(adapter);
        locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location selected = (Location) adapterView.getItemAtPosition(i);
                Intent locationDetailIntent = new Intent(LocationsActivity.this, LocationDetailActivity.class);
                locationDetailIntent.putExtra("LOCATION_ID", selected.getId());
                locationDetailIntent.putExtra("SOURCE", LocationDetailActivity.DETAIL_SOURCE_CODE);
                startActivity(locationDetailIntent);
            }
        });
    }
}
