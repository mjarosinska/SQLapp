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

    Button button;
    TextView tv, tv2;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        db = new DatabaseHandler(this);
    }

    public void onClick(View v) {
        db.deleteTable();
        db.deleteAllPoints();

        db.addNewLocationPoint(new Location(5.55, 8.1188, "2017-01-12 12:34:11", 1.3334223f));
        float[] values = {3.2222f, 1222.11f, 13.222f};
        db.addNewAccelerationPoint(new Acceleration(values));
        db.addNewBooleanPoint(new Bool(true));

        List<Location> location = db.getAllLocationPoints();
        List<Acceleration> acceleration = db.getAllAccelerationPoints();
        List<Bool> bool = db.getAllBooleanPoints();

        for (Location loc : location) {
            tv.setText(tv.getText() + "\n" + loc.getLatitude() + ", " + loc.getLongitude() //);
                    + " godz: " + loc.getTime() + ", " + loc.getSpeed());
        }

        for (Bool a : bool)
        {
            tv2.setText(tv2.getText() + "\nbool: " + a.getB() + " time: " + a.getTime());
        }
    }


}

