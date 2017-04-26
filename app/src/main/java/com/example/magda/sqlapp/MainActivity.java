package com.example.magda.sqlapp;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    ArrayList<Long> a = new ArrayList<>();
    Button button;
    TextView tv;
    DatabaseHandler db; //= new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView);
        db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */

        // Inserting:
        //Log.d("Insert: ", "Inserting ..");
        // db.addPoint(new Location (1.111, 533.3));
        // db.addPoint(new Location (0.222, -0.2));


        //Deleting:
        /*Location point= new Location(12, 7.77777, 777.77777);
        db.deletePoint(point);*/

        // Reading all points
        //Log.d("Reading: ", "Reading all contacts..");
    }

    public void onClick(View v) {
        db.deleteTable();
        db.deleteAllPoints();
        db.addNewPoint(new Location(5.55, 8.1188, "2017-01-12 12:33:11", 1.3334223f));
        List<Location> location = db.getAllPoints();

        for (Location loc : location) {
            //String log = "Id: "+ loc.getId()+" , Latitude: " + loc.getLatitude() + " , Longitude: " + loc.getLongitude();

            tv.setText(tv.getText() + "\n" + loc.getLatitude() + ", " + loc.getLongitude() //);
                    + " o godz. " + loc.getTime() + ", " + loc.getSpeed());
        }
    }


}

