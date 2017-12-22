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

public class FoodDetailActivity extends AppCompatActivity {

    public static final int NEW_SOURCE_CODE = 1;
    public static final int DETAIL_SOURCE_CODE = 2;
    public static final int EDIT_SOURCE_CODE =3;

    private Spinner typeSpinner;
    private ArrayAdapter<CharSequence> typeSpinnerAdapter;
    private Button save;
    private Button cancel;
    private RelativeLayout layout;

    private EditText name;
    private EditText quantity;
    private EditText price;
    private Spinner locationSpinner;
    private ArrayAdapter<Location> locationSpinnerAdapter;
    private EditText expirationDate;
    private EditText description;

    private Food food;

    private DataProvider dataProvider;


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        if(getIntent().getIntExtra("SOURCE", -1) == DETAIL_SOURCE_CODE){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.food_detail_menu, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_food:
                Intent editIntent = new Intent(FoodDetailActivity.this, FoodDetailActivity.class);
                editIntent.putExtra("SOURCE", EDIT_SOURCE_CODE);
                editIntent.putExtra("FOOD_ID", food.getId());
                startActivity(editIntent);
                return true;
            case R.id.delete_food:
                dataProvider.deleteFood(food);
                returnMainActivity();
                return true;
            case R.id.use_food:
                food.setUsageCode(Food.USED);
                dataProvider.updateFood(food);
                returnMainActivity();
                return true;
            case R.id.throw_food:
                food.setUsageCode(Food.TOSSED);
                dataProvider.updateFood(food);
                returnMainActivity();
                return true;
        }
        return false;
    }

    private void returnMainActivity() {
        Intent homeIntent = new Intent(FoodDetailActivity.this, MainActivity.class);
        startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataProvider = new DataProvider(this);
        dataProvider.open();

        food = dataProvider.getFood(getIntent().getIntExtra("FOOD_ID", -1));

        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        name = findViewById(R.id.nameEditText);
        quantity = findViewById(R.id.quantityEditText);
        price = findViewById(R.id.priceEditView);
        locationSpinner = findViewById(R.id.locationSpinner);
        expirationDate = findViewById(R.id.expirationEditText);
        description = findViewById(R.id.descriptionEditText);

        switch (getIntent().getIntExtra("SOURCE", -1)){
            case NEW_SOURCE_CODE:
                populateSpinner();
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkFields()){
                            Food food = new Food();
                            addData(food);
                            dataProvider.addFood(food);
                            returnMainActivity();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        returnMainActivity();
                    }
                });
                break;
            case DETAIL_SOURCE_CODE:
                layout = findViewById(R.id.food_detail_layout);
                layout.removeViewInLayout(save);
                layout.removeViewInLayout(cancel);
                populateSpinner();
                setFields(food);
                enableFields(false);
                break;
            case EDIT_SOURCE_CODE:
                populateSpinner();
                setFields(food);
                enableFields(true);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkFields()){
                            addData(food);
                            dataProvider.updateFood(food);
                            returnMainActivity();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        returnMainActivity();
                    }
                });
                break;
        }
    }

    private void addData(Food food) {
        food.setName(name.getText().toString().trim());
        food.setType(typeSpinner.getSelectedItem().toString().trim());
        food.setPrice(Double.valueOf(price.getText().toString().trim()));
        food.setExpiration(expirationDate.getText().toString().trim());
        food.setDescription(description.getText().toString().trim());
        food.setQuantity(Double.valueOf(quantity.getText().toString().trim()));
        Location location = (Location) locationSpinner.getSelectedItem();
        food.setLocation(location.getId());
        food.setUsageCode(Food.GOOD);
    }

    private void populateSpinner() {
        typeSpinner = findViewById(R.id.typeSpinner);
        typeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.food_types, android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);


        locationSpinner = findViewById(R.id.locationSpinner);
        ArrayList<Location> locations = dataProvider.getLocations();
        locationSpinnerAdapter = new ArrayAdapter<Location>(this, android.R.layout.simple_list_item_1, locations);
        locationSpinner.setAdapter(locationSpinnerAdapter);
    }

    private boolean checkFields(){
        if(name.getText().toString().trim() != null &&
                quantity.getText().toString().trim() != null &&
                price.getText().toString().trim() != null &&
                expirationDate.getText().toString().trim() != null){
            return true;
        } else {
            return  false;
        }
    }

    private void setFields(Food food){
        name.setText(food.getName());
        String type = food.getType();
        int spinnerTypePosition = typeSpinnerAdapter.getPosition(type);
        typeSpinner.setSelection(spinnerTypePosition);
        Location location = dataProvider.getLocation(food.getLocation());
        int spinnerLocationPosition = locationSpinnerAdapter.getPosition(location);
        locationSpinner.setSelection(spinnerLocationPosition);
        price.setText(Double.toString(food.getPrice()));
        expirationDate.setText(food.getExpiration());
        description.setText(food.getDescription());
        quantity.setText(Double.toString(food.getQuantity()));
        locationSpinner.getSelectedItem();

    }

    private void enableFields(boolean bool){
        name.setEnabled(bool);
        typeSpinner.setEnabled(bool);
        locationSpinner.setEnabled(bool);
        price.setEnabled(bool);
        expirationDate.setEnabled(bool);
        description.setEnabled(bool);
        quantity.setEnabled(bool);
    }
}
