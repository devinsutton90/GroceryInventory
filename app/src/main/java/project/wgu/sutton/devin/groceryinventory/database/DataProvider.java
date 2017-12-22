package project.wgu.sutton.devin.groceryinventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import project.wgu.sutton.devin.groceryinventory.model.Food;
import project.wgu.sutton.devin.groceryinventory.model.Location;

public class DataProvider {

    private DBOpenHelper openHelper;
    private SQLiteDatabase database;

    public DataProvider(Context context) {
        openHelper = new DBOpenHelper(context);
    }

    public void open() {
        database = openHelper.getWritableDatabase();
    }

    public void close() {
        openHelper.close();
    }

    public void addFood(Food food){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.FOOD_NAME, food.getName());
        values.put(DBOpenHelper.FOOD_EXPIRATION, food.getExpiration());
        values.put(DBOpenHelper.FOOD_PRICE, food.getPrice());
        values.put(DBOpenHelper.FOOD_QUANTITY, food.getQuantity());
        values.put(DBOpenHelper.FOOD_USAGE_CODE, food.getUsageCode());
        values.put(DBOpenHelper.FOOD_TYPE, food.getType());
        values.put(DBOpenHelper.FOOD_LOCATION_ID, food.getLocation());
        values.put(DBOpenHelper.FOOD_DETAILS, food.getDescription());
        database.insert(DBOpenHelper.TABLE_FOOD, null, values);
    }

    public void deleteFood(Food food){
        String where = DBOpenHelper.FOOD_ID + "=" + food.getId();
        database.delete(DBOpenHelper.TABLE_FOOD, where, null);
    }

    public void updateFood(Food food){
        String where = DBOpenHelper.FOOD_ID + "=" + food.getId();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.FOOD_NAME, food.getName());
        values.put(DBOpenHelper.FOOD_EXPIRATION, food.getExpiration());
        values.put(DBOpenHelper.FOOD_PRICE, food.getPrice());
        values.put(DBOpenHelper.FOOD_QUANTITY, food.getQuantity());
        values.put(DBOpenHelper.FOOD_USAGE_CODE, food.getUsageCode());
        values.put(DBOpenHelper.FOOD_TYPE, food.getType());
        values.put(DBOpenHelper.FOOD_DETAILS, food.getDescription());
        values.put(DBOpenHelper.FOOD_LOCATION_ID, food.getLocation());
        database.update(DBOpenHelper.TABLE_FOOD, values, where, null);
    }

    public void addLocation(Location location){
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.LOCATION_NAME, location.getName());
        values.put(DBOpenHelper.LOCATION_TYPE, location.getType());
        database.insert(DBOpenHelper.TABLE_LOCATION, null, values);

    }

    public void deleteLocation(Location location){
        String where = DBOpenHelper.LOCATION_ID + "=" + location.getId();
        database.delete(DBOpenHelper.TABLE_LOCATION, where, null);
    }

    public void updateLocation(Location location){
        String where = DBOpenHelper.LOCATION_ID + "=" + location.getId();
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.LOCATION_NAME, location.getName());
        values.put(DBOpenHelper.LOCATION_TYPE, location.getType());
        database.update(DBOpenHelper.TABLE_LOCATION, values, where, null);
    }

    public Food getFood(int id){
        Food result =  new Food();
        Cursor cursor = database.query(DBOpenHelper.TABLE_FOOD, DBOpenHelper.FOOD_ALL_COLUMNS,
                DBOpenHelper.FOOD_ID + "= ?", new String[] {String.valueOf(id)}, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
            }
        }
        return result;
    }

    public Location getLocation(int id){
        Location result =  new Location();
        Cursor cursor = database.query(DBOpenHelper.TABLE_LOCATION, DBOpenHelper.LOCATION_ALL_COLUMNS,
                DBOpenHelper.LOCATION_ID + "= ?", new String[] {String.valueOf(id)}, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.LOCATION_NAME)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.LOCATION_TYPE)));
            }
        }
        return result;
    }

    public ArrayList<Food> getFoodByName(){
        ArrayList<Food> results = new ArrayList<>();

        Cursor cursor = database.query(DBOpenHelper.TABLE_FOOD, DBOpenHelper.FOOD_ALL_COLUMNS,
                DBOpenHelper.FOOD_USAGE_CODE + "= ?", new String[] {String.valueOf(Food.GOOD)}, null, null, DBOpenHelper.FOOD_NAME + " DESC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Food result = new Food();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
                results.add(result);
            }
        }
        return results;
    }

    public ArrayList<Food> getFoodByLocation(){
        ArrayList<Food> results = new ArrayList<>();

        Cursor cursor = database.query(DBOpenHelper.TABLE_FOOD, DBOpenHelper.FOOD_ALL_COLUMNS,
                DBOpenHelper.FOOD_USAGE_CODE + "= ?", new String[] {String.valueOf(Food.GOOD)}, null, null, DBOpenHelper.FOOD_LOCATION_ID + " DESC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Food result = new Food();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
                results.add(result);
            }
        }
        return results;
    }

    public ArrayList<Food> getFoodByExpiration(){
        ArrayList<Food> results = new ArrayList<>();

        Cursor cursor = database.query(DBOpenHelper.TABLE_FOOD, DBOpenHelper.FOOD_ALL_COLUMNS,
                DBOpenHelper.FOOD_USAGE_CODE + "= ?", new String[] {String.valueOf(Food.GOOD)}, null, null, DBOpenHelper.FOOD_EXPIRATION + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Food result = new Food();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
                results.add(result);
            }
        }
        return results;
    }

    public ArrayList<Food> getFoodByType(){
        ArrayList<Food> results = new ArrayList<>();

        Cursor cursor = database.query(DBOpenHelper.TABLE_FOOD, DBOpenHelper.FOOD_ALL_COLUMNS,
                DBOpenHelper.FOOD_USAGE_CODE + "= ?", new String[] {String.valueOf(Food.GOOD)}, null, null, DBOpenHelper.FOOD_TYPE + " DESC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Food result = new Food();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
                results.add(result);
            }
        }
        return results;
    }

    public ArrayList<Food> getFoodExpiringOnDate(String date){
        ArrayList<Food> results = new ArrayList<>();

        String statement = "SELECT * FROM " + DBOpenHelper.TABLE_FOOD + " WHERE " + DBOpenHelper.FOOD_USAGE_CODE + "=" +
                Food.GOOD + " AND " + DBOpenHelper.FOOD_EXPIRATION + "=" + date;
        Cursor cursor = database.rawQuery(statement, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Food result = new Food();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
                results.add(result);
            }
        }
        return results;
    }

    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        Cursor cursor = database.query(DBOpenHelper.TABLE_LOCATION, DBOpenHelper.LOCATION_ALL_COLUMNS,
                null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Location result = new Location();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.LOCATION_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.LOCATION_NAME)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.LOCATION_TYPE)));

                locations.add(result);
            }
        }

        return locations;
    }

    public ArrayList<Food> getLocationFood(int id) {
        ArrayList<Food> foodList = new ArrayList<>();
        Cursor cursor = database.query(DBOpenHelper.TABLE_FOOD, DBOpenHelper.FOOD_ALL_COLUMNS,
                DBOpenHelper.LOCATION_ID + "= ?", new String[] {String.valueOf(id)}, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Food result =  new Food();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
                foodList.add(result);
            }
        }
        return foodList;
    }

    public ArrayList<Food> getWastedFood(){
        ArrayList<Food> results = new ArrayList<>();

        Cursor cursor = database.query(DBOpenHelper.TABLE_FOOD, DBOpenHelper.FOOD_ALL_COLUMNS,
                DBOpenHelper.FOOD_USAGE_CODE + "= ?", new String[] {String.valueOf(Food.TOSSED)}, null, null, DBOpenHelper.FOOD_TYPE + " DESC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Food result = new Food();
                result.setId(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_ID)));
                result.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_NAME)));
                result.setExpiration(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_EXPIRATION)));
                result.setPrice(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_PRICE)));
                result.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.FOOD_QUANTITY)));
                result.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_DETAILS)));
                result.setType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.FOOD_TYPE)));
                result.setLocation(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_LOCATION_ID)));
                result.setUsageCode(cursor.getInt(cursor.getColumnIndex(DBOpenHelper.FOOD_USAGE_CODE)));
                results.add(result);
            }
        }
        return results;
    }

    public boolean foodExpiringSoonCheck(String date){
        ArrayList<Food> list = getFoodExpiringOnDate(date);
        if (list.isEmpty()){
            return false;
        } else {
            return true;
        }

    }
}