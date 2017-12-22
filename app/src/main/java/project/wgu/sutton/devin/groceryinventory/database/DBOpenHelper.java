package project.wgu.sutton.devin.groceryinventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "grocery.db";
    private static final int DATABASE_VERSION = 1;
    //Food Table definition
    public static final String TABLE_FOOD = "food";
    public static final String FOOD_ID = "_id";
    public static final String FOOD_NAME = "name";
    public static final String FOOD_QUANTITY = "qty";
    public static final String FOOD_PRICE = "price";
    public static final String FOOD_TYPE = "type";
    public static final String FOOD_DETAILS = "details";
    public static final String FOOD_EXPIRATION = "expiration";
    public static final String FOOD_LOCATION_ID = "locationID";
    public static final String FOOD_USAGE_CODE = "usedCode";
    public static final String[] FOOD_ALL_COLUMNS= {FOOD_ID, FOOD_NAME, FOOD_QUANTITY,
            FOOD_PRICE, FOOD_TYPE, FOOD_TYPE, FOOD_EXPIRATION, FOOD_LOCATION_ID, FOOD_USAGE_CODE, FOOD_DETAILS};
    //Location table definition
    public static final String TABLE_LOCATION = "location";
    public static final String LOCATION_ID = "_id";
    public static final String LOCATION_NAME = "name";
    public static final String LOCATION_TYPE = "type";
    public static final String[] LOCATION_ALL_COLUMNS= {LOCATION_ID, LOCATION_NAME, LOCATION_TYPE};
    //Create Table Statments
    public static final String FOOD_TABLE_CREATE =
            "CREATE TABLE " + TABLE_FOOD + " (" +
                    FOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FOOD_NAME + " TEXT, " +
                    FOOD_QUANTITY + " DOUBLE, " +
                    FOOD_PRICE + " DOUBLE, " +
                    FOOD_TYPE + " TEXT, " +
                    FOOD_EXPIRATION + " TEXT, " +
                    FOOD_USAGE_CODE + " INTEGER, " +
                    FOOD_DETAILS + " TEXT, " +
                    FOOD_LOCATION_ID + " INTEGER, " +
                    "FOREIGN KEY(" + FOOD_LOCATION_ID + ") REFERENCES " +
                    TABLE_LOCATION + "(" + LOCATION_ID + "))";
    public static final String LOCATION_TABLE_CREATE =
            "CREATE TABLE " + TABLE_LOCATION + " (" +
                    LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LOCATION_NAME + " TEXT, " +
                    LOCATION_TYPE + " TEXT)";
    //Constructor
    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LOCATION_TABLE_CREATE);
        sqLiteDatabase.execSQL(FOOD_TABLE_CREATE);
        loadDefaultLocations(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(sqLiteDatabase);
    }

    private void loadDefaultLocations(SQLiteDatabase sqLiteDatabase){
        ContentValues refridgeratorValues = new ContentValues();
        refridgeratorValues.put(LOCATION_TYPE, "Refrigerator");
        refridgeratorValues.put(LOCATION_NAME, "Refrigerator");
        ContentValues freezerValues = new ContentValues();
        freezerValues.put(LOCATION_TYPE, "Freezer");
        freezerValues.put(LOCATION_NAME, "Freezer");
        ContentValues pantryValues = new ContentValues();
        pantryValues.put(LOCATION_TYPE, "Pantry");
        pantryValues.put(LOCATION_NAME, "Pantry");
        sqLiteDatabase.insert(TABLE_LOCATION, null, refridgeratorValues);
        sqLiteDatabase.insert(TABLE_LOCATION, null, freezerValues);
        sqLiteDatabase.insert(TABLE_LOCATION, null, pantryValues);

    }

}
