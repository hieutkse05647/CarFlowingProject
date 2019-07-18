package com.ebookfrenzy.carflowingproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ebookfrenzy.carflowingproject.DAO.FireStoreDAO;
import com.ebookfrenzy.carflowingproject.Model.Location;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    FireStoreDAO fireStoreDAO;
    Location lastLocation;
    ArrayList<String> listCarPlate = new ArrayList<String>();
    public String carPlate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateFireStoreDAO();
        Button button = (Button) findViewById(R.id.myButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listCarPlate.size() > 0) {
                    int index = (int) (Math.random() * listCarPlate.size());
                    System.out.println(index);
                    setCarPlate(listCarPlate.get(index));
                }
            }
        });
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
        if (fireStoreDAO != null) {
            fireStoreDAO.listenChangeLocationListener();
        }
    }

    public void setLastLocation(Location location) {
        this.lastLocation = location;
        setTextView2();
    }

    public void setListCarPlate(ArrayList<String> listCarPlate) {
        this.listCarPlate = listCarPlate;
        setTextView();
    }

    public void setTextView() {
        TextView textView = (TextView) findViewById(R.id.textView);
        String text = "";
        Iterator iter = this.listCarPlate.iterator();
        while (iter.hasNext()) {
            text += iter.next().toString() + "\n";
        }
        textView.setText(text);
    }

    public void setTextView2() {
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(this.carPlate + "\n" + this.lastLocation.getGeocode());
    }

    public void initiateFireStoreDAO () {
        fireStoreDAO = new FireStoreDAO(MainActivity.this);
        fireStoreDAO.execute();
    }
}
