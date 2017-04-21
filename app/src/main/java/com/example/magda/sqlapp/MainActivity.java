package com.example.magda.sqlapp;

import android.app.Activity;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ArrayList<Long> a = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)findViewById(R.id.textView);

        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        db.deleteAllPoints();
        // Inserting:
        //Log.d("Insert: ", "Inserting ..");
        db.addPoint(new Location (5551.32, 533.3));
        //db.addPoint(new Location (1.111, 533.3));
        //db.addPoint(new Location (0.222, -0.2));


        //Deleting:
        /*Location point= new Location(12, 7.77777, 777.77777);
        db.deletePoint(point);*/

        // Reading all points
        //Log.d("Reading: ", "Reading all contacts..");
        List<Location> location = db.getAllPoints();

        for (Location loc : location) {
            //String log = "Id: "+ loc.getId()+" , Latitude: " + loc.getLatitude() + " , Longitude: " + loc.getLongitude();

            tv.setText(tv.getText() + "\n" + "Id: "+ loc.getId()+", Lati: " + loc.getLatitude() + ", Long: " + loc.getLongitude() );

        }

    }
}
