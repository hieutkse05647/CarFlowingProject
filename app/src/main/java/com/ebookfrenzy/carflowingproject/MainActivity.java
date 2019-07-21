package com.ebookfrenzy.carflowingproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ebookfrenzy.carflowingproject.DAO.FireStoreDAO;
import com.ebookfrenzy.carflowingproject.Model.Location;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ActionBar toolbar;
    FireStoreDAO fireStoreDAO;
    Location lastLocation;
    ArrayList<String> listCarPlate = new ArrayList<String>();
    public String carPlate = "";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    return true;
                case R.id.navigation_activity:
//                    Intent intent = new Intent(this, ActiveCar.class);
//                    startActivity(intent);
                    return true;
                case R.id.navigation_cars:
                    toolbar.setTitle("Cars");
                    return true;
                case R.id.navigation_alarm:
                    toolbar.setTitle("Alarm");
                    return true;
                case R.id.navigation_user:
                    toolbar.setTitle("User");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle("Following Car Project");
        initiateFireStoreDAO();
    }

    public void ActiveCarActivity (View view){
        Intent intent = new Intent(this, ActiveCar.class );
        startActivity(intent);
    }

    public void TrackingGPSActivity (View view){
        Intent intent = new Intent(this, GPSActivity.class );
        startActivity(intent);
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
        if (fireStoreDAO != null) {
            fireStoreDAO.listenChangeLocationListener();
        }
    }

    public void setLastLocation(Location location) {
        this.lastLocation = location;
    }

    public void setListCarPlate(ArrayList<String> listCarPlate) {
        this.listCarPlate = listCarPlate;
    }

    public void initiateFireStoreDAO () {
        fireStoreDAO = new FireStoreDAO(MainActivity.this);
        fireStoreDAO.execute();
    }
}
