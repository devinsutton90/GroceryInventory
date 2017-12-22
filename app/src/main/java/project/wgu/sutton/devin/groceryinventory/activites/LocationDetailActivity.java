package project.wgu.sutton.devin.groceryinventory.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import project.wgu.sutton.devin.groceryinventory.R;
import project.wgu.sutton.devin.groceryinventory.database.DataProvider;
import project.wgu.sutton.devin.groceryinventory.model.Food;
import project.wgu.sutton.devin.groceryinventory.model.Location;

public class LocationDetailActivity extends AppCompatActivity {

    public static final int NEW_SOURCE_CODE = 1;
    public static final int DETAIL_SOURCE_CODE = 2;
    public static final int EDIT_SOURCE_CODE =3;
    private int id;
    private int source;
    private DataProvider dataProvider;
    private Location location;

    private EditText name;
    private Spinner typeSpinner;
    private ArrayAdapter<CharSequence> typeSpinnerAdapter;
    private Button save;
    private Button cancel;
    private RelativeLayout layout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(getIntent().getIntExtra("SOURCE", -1) == DETAIL_SOURCE_CODE){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.location_detail_menu, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_location:
                Intent editIntent = new Intent(LocationDetailActivity.this, LocationDetailActivity.class);
                editIntent.putExtra("SOURCE", EDIT_SOURCE_CODE);
                editIntent.putExtra("LOCATION_ID", location.getId());
                startActivity(editIntent);
                return true;
            case R.id.delete_location:
                if (checkLocationEmpty(id)){
                    dataProvider.deleteLocation(location);
                    returnMainActivity();
                    return true;
                } else {
                    System.out.println("Location cannot be deleted if not empty");
                }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.nameEditText);
        typeSpinner = findViewById(R.id.locationTypeSpinner);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        layout = findViewById(R.id.locationDetailLayout);

        source = getIntent().getIntExtra("SOURCE", -1);
        id = getIntent().getIntExtra("LOCATION_ID", -1);

        dataProvider = new DataProvider(this);
        dataProvider.open();

        populateSpinner();

        switch (source){
            case NEW_SOURCE_CODE:
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        returnMainActivity();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkFields()){
                            Location newLocation = new Location();
                            newLocation.setName(name.getText().toString().trim());
                            newLocation.setType(typeSpinner.getSelectedItem().toString().trim());
                            dataProvider.addLocation(newLocation);
                            returnMainActivity();
                        } else {
                            System.out.println("Missing fields");
                        }
                    }
                });
                break;
            case DETAIL_SOURCE_CODE:
                location = dataProvider.getLocation(id);
                populateFields(location);
                enableFields(false);
                layout.removeViewInLayout(save);
                layout.removeViewInLayout(cancel);
                break;
            case EDIT_SOURCE_CODE:
                location = dataProvider.getLocation(id);
                populateFields(location);
                enableFields(true);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        returnMainActivity();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkFields()){
                            location.setName(name.getText().toString().trim());
                            location.setType(typeSpinner.getSelectedItem().toString().trim());
                            dataProvider.updateLocation(location);
                            returnMainActivity();
                        } else {
                            System.out.println("Missing fields");
                        }
                    }
                });
                break;
        }

    }


    private void populateFields(Location location){
        name.setText(location.getName());

        String type = location.getType();
        int spinnerTypePosition = typeSpinnerAdapter.getPosition(type);
        typeSpinner.setSelection(spinnerTypePosition);

    }
    private void populateSpinner() {
        typeSpinner = findViewById(R.id.locationTypeSpinner);
        typeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.location_type, android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
    }
    private void enableFields(boolean bool){
        name.setEnabled(bool);
        typeSpinner.setEnabled(bool);
    }
    private void returnMainActivity() {
        Intent homeIntent = new Intent(LocationDetailActivity.this, MainActivity.class);
        startActivity(homeIntent);
    }
    private boolean checkFields(){
        if(name.getText().toString().trim() != null){
            return true;
        } else {
            return  false;
        }
    }
    private boolean checkLocationEmpty(int id){
        ArrayList<Food> foods = dataProvider.getLocationFood(id);
        if(foods.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

}
