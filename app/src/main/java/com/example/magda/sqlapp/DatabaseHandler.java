package com.example.magda.sqlapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    // Contacts Table Columns names
    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_LATITUDE = "latitude";
    private static final String COLUMN_NAME_LONGITUDE = "longitude";
    private static final String COLUMN_NAME_TIMESTAMP = "timeStamp";

    long rowId;
    Timestamp currentTimestamp;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
                + COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_LATITUDE + " REAL, "
                + COLUMN_NAME_LONGITUDE + " REAL"  + ")";
                //+ COLUMN_NAME_TIMESTAMP + " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        // Create tables again
        onCreate(db);

    }

    // CRUD (Create, Read, Update, Delete) operations:

    // Adding new point
    public void addPoint(Location location) {
        SQLiteDatabase db = this.getReadableDatabase();
        //currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());


        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_LATITUDE, location.getLatitude());
        values.put(COLUMN_NAME_LONGITUDE, location.getLongitude());
        //values.put(COLUMN_NAME_TIMESTAMP, getTime());

        //values.put(COLUMN_TIMESTAMP, " time('now') " );
        //values.put(COLUMN_TIMESTAMP, getTime(db));

        // Inserting Row

        db.insert(TABLE_LOCATION, null, values);
        //db.execSQL("INSERT INTO "+COLUMN_NAME_TIMESTAMP+" VALUES (null, datetime()) ");
        db.close(); // Closing database connection
    }

    public String getTime() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return timeStamp;
    }

    // Getting single point
    public Location getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LOCATION, new String[] { COLUMN_NAME_ID,
                        COLUMN_NAME_LATITUDE, COLUMN_NAME_LONGITUDE }, COLUMN_NAME_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Location location = new Location(cursor.getInt(0), cursor.getDouble(1), cursor.getDouble(2));
        // return location
        return location;
    }

    // Getting All Points
    public List<Location> getAllPoints() {
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
                //location.setTime(cursor.getString(3));
                // Adding points to list
                pointsList.add(location);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return contact list
        return pointsList;
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

    // Updating single point
    public int updatePoint(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_LATITUDE, location.getLatitude());
        values.put(COLUMN_NAME_LONGITUDE, location.getLongitude());

        // updating row
        return db.update(TABLE_LOCATION, values, COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(location.getId()) });
    }

    // Deleting single point
    public void deletePoint(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATION, COLUMN_NAME_ID + " = ?",
                new String[] { String.valueOf(location.getId()) });
        db.close();
    }

    public void deleteAllPoints() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_LOCATION);
    }



}