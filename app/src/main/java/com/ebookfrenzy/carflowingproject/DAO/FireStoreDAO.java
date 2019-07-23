package com.ebookfrenzy.carflowingproject.DAO;

import android.os.AsyncTask;

import com.ebookfrenzy.carflowingproject.Model.Car;
import com.ebookfrenzy.carflowingproject.Model.MapLocation;
import com.ebookfrenzy.carflowingproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FireStoreDAO extends AsyncTask<Void, Integer, Void> {

    FirebaseFirestore db = null;
    Query query = null;
    ListenerRegistration carPlateListener;
    ListenerRegistration locationListener;
    public ArrayList<Car> listCars = new ArrayList<Car>();
    public String carPlate = "";
    GoogleMap googleMap = null;

    private static FireStoreDAO fireStoreDAO;

    public FireStoreDAO() {
        this.db = FirebaseFirestore.getInstance();
    }

    public static FireStoreDAO getInstance() {
        if (fireStoreDAO == null) {
            fireStoreDAO = new FireStoreDAO();
        }
        return fireStoreDAO;
    }

    public void listenChangeListCarPlate() {
        carPlateListener = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot snapshot,
                                        FirebaseFirestoreException e) {
                        if (e != null) {
                            System.out.println("Error getting documents.");
                        }

                        if (snapshot != null && !snapshot.isEmpty()) {
                            final ArrayList<String> listCarPlate = new ArrayList<String>();
                            final ArrayList<Car> listAvalaibleCars = new ArrayList<Car>();
                            for (QueryDocumentSnapshot document : snapshot) {
                                if (!listCarPlate.contains(document.getData().get("carPlate").toString())) {
                                    listCarPlate.add(document.getData().get("carPlate").toString());
                                    Car car = new Car(document.getData().get("carPlate").toString(), document.getData().get("name").toString());
                                    listAvalaibleCars.add(car);
                                }
                            }
                            listCars = listAvalaibleCars;
                        }
                    }
                });
    }
    public void listenChangeLocationListener() {
        locationListener = query.whereEqualTo("carPlate", carPlate).orderBy("time", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshot,
                                FirebaseFirestoreException e) {
                if (e != null) {
                    System.out.println("Error getting documents.");
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    final ArrayList<String> listCarPlate = new ArrayList<String>();
                    DocumentSnapshot document = snapshot.getDocuments().get(0);
                    MapLocation mapLocation = new MapLocation(
                            document.getData().get("carPlate").toString(),
                            Float.parseFloat(document.getData().get("bearing").toString()),
                            Float.parseFloat(document.getData().get("latitude").toString()),
                            Float.parseFloat(document.getData().get("longitude").toString()),
                            document.getData().get("geocode").toString(),
                            document.getData().get("time").toString()
                    );
                    if (googleMap != null) {
                        googleMap.clear();
                        LatLng localFPT = new LatLng(mapLocation.getLatitude(), mapLocation.getLongitude());
                        MarkerOptions marker = new MarkerOptions().position(localFPT).title(carPlate);
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_truck_marker));
                        marker.rotation(mapLocation.getBearing());
                        googleMap.addMarker(marker);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(localFPT));
                        googleMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
                    }

                }
            }
        });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        query = this.db.collection("track_n_traces");
        if (carPlateListener != null) {
            carPlateListener.remove();
        }
        if (locationListener != null) {
            locationListener.remove();
        }
    }
    @Override
    protected Void doInBackground(Void... voids) {
        listenChangeListCarPlate();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public ArrayList<Car> getListCars() {
        return listCars;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        if (locationListener != null) {
            locationListener.remove();
        }
        this.carPlate = carPlate;
        listenChangeLocationListener();
    }

    public void setGoogleMap(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
