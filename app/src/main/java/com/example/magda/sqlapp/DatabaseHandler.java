package com.example.magda.sqlapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MAGDA on 2017-03-30.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "location";

    // Location table name
    private static final String TABLE_LOCATION = "loc";
    // Acceleration table name
    private static final String TABLE_ACCELERATION = "acc";
    // Boolean table name
    private static final String TABLE_BOOLEAN = "bool";

    // Location Table Columns names
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_LATITUDE = "latitude";
    private static final String COLUMN_NAME_LONGITUDE = "longitude";
    private static final String COLUMN_NAME_TIMESTAMP = "time";
    private static final String COLUMN_NAME_SPEED = "speed";

    // Acceleration Table Columns names
    private static final String COLUMN_NAME_ID_ACC = "id";
    private static final String COLUMN_NAME_X = "x";
    private static final String COLUMN_NAME_Y = "y";
    private static final String COLUMN_NAME_Z = "z";

    // Boolean Table Columns names
    private static final String COLUMN_NAME_ID_BOOL = "id";
    private static final String COLUMN_NAME_BOOLEAN = "boolean";

    // Current time
    String timestamp;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME_LATITUDE + " REAL, "
                + COLUMN_NAME_LONGITUDE + " REAL, "
                + COLUMN_NAME_TIMESTAMP + " default current_timestamp, "
                + COLUMN_NAME_SPEED + " REAL);";

        String CREATE_ACCELERATION_TABLE = "CREATE TABLE " + TABLE_ACCELERATION + "("
                + COLUMN_NAME_ID_ACC + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME_X + " REAL, "
                + COLUMN_NAME_Y + " REAL, "
                + COLUMN_NAME_Z + " REAL, "
                + COLUMN_NAME_TIMESTAMP + " default current_timestamp);";

        String CREATE_BOOLEAN_TABLE = "CREATE TABLE " + TABLE_BOOLEAN + "("
                + COLUMN_NAME_ID_BOOL + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME_BOOLEAN + " INTEGER, "
                + COLUMN_NAME_TIMESTAMP + " default current_timestamp);";

        db.execSQL(CREATE_LOCATION_TABLE);
        db.execSQL(CREATE_ACCELERATION_TABLE);
        db.execSQL(CREATE_BOOLEAN_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCELERATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOLEAN);

        // Create tables again
        onCreate(db);
    }

    // Adding new point to Location
    public void addNewLocationPoint(Location location) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values_loc = new ContentValues();
        values_loc.put(COLUMN_NAME_LATITUDE, location.getLatitude());
        values_loc.put(COLUMN_NAME_LONGITUDE, location.getLongitude());
        values_loc.put(COLUMN_NAME_TIMESTAMP, location.getTime());
        values_loc.put(COLUMN_NAME_SPEED, location.getSpeed());

        // Inserting Row
        try {
            db.insert(TABLE_LOCATION, null, values_loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
       db.close(); // Closing database connection
    }

    // Adding new point to Acceleration
    public void addNewAccelerationPoint(Acceleration acceleration) {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long date = new Date().getTime();
        timestamp = s.format(date);
        //System.out.println("czas = " + timestamp.toString());

        ContentValues values_acc = new ContentValues();
        values_acc.put(COLUMN_NAME_X, acceleration.getX());
        values_acc.put(COLUMN_NAME_Y, acceleration.getY());
        values_acc.put(COLUMN_NAME_Z, acceleration.getZ());
        values_acc.put(COLUMN_NAME_TIMESTAMP, timestamp);
        acceleration.setTime(timestamp);

        // Inserting Row
        try {
            db.insert(TABLE_ACCELERATION, null, values_acc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close(); // Closing database connection
    }

    public void addNewBooleanPoint(Bool b) {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long date = new Date().getTime();
        timestamp = s.format(date);

        ContentValues values_bool = new ContentValues();
        values_bool.put(COLUMN_NAME_BOOLEAN, b.getB());
        values_bool.put(COLUMN_NAME_TIMESTAMP, timestamp);
        b.setTime(timestamp);

        // Inserting Row
        try {
            db.insert(TABLE_BOOLEAN, null, values_bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close(); // Closing database connection
    }

    // Getting All Points from Location Table
    public List<Location> getAllLocationPoints() {
        List<Location> pointsList = new ArrayList<Location>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location location = new Location();
                location.setId(Integer.parseInt(cursor.getString(0)));
                location.setLatitude(cursor.getDouble(1));
                location.setLongitude(cursor.getDouble(2));
                location.setTime(cursor.getString(3));
                location.setSpeed(cursor.getFloat(4));
                // Adding points to list
                pointsList.add(location);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return pointsList;
    }

    // Getting All Points from Acceleration Table
    public List<Acceleration> getAllAccelerationPoints() {
        List<Acceleration> accList = new ArrayList<Acceleration>();
        String selectQuery = "SELECT  * FROM " + TABLE_ACCELERATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Acceleration acceleration = new Acceleration();
                acceleration.setId(Integer.parseInt(cursor.getString(0)));
                acceleration.setX(cursor.getFloat(1));
                acceleration.setY(cursor.getFloat(2));
                acceleration.setZ(cursor.getFloat(3));
                acceleration.setTime(cursor.getString(4));
                // Adding points to list
                accList.add(acceleration);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return accList;
    }

    // Getting All Points from Boolean Table
    public List<Bool> getAllBooleanPoints() {
        List<Bool> boolList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_BOOLEAN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Bool bool_b = new Bool();
                bool_b.setId(Integer.parseInt(cursor.getString(0)));
                bool_b.setB(cursor.getInt(1));
                bool_b.setTime(cursor.getString(2));
                // Adding points to list
                boolList.add(bool_b);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return boolList;
    }

    // Deleting all points from all tables
    public void deleteAllPoints() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_LOCATION);
        db.execSQL("delete from " + TABLE_ACCELERATION);
        db.execSQL("delete from " + TABLE_BOOLEAN);
    }

    public void deleteTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCELERATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOLEAN);
        onCreate(db);
    }

    // Getting points Count
    public int getPointsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
