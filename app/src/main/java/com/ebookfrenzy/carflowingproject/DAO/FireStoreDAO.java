package com.ebookfrenzy.carflowingproject.DAO;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ebookfrenzy.carflowingproject.MainActivity;
import com.ebookfrenzy.carflowingproject.Model.Location;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    MainActivity contextParent;
    ListenerRegistration carPlateListener;
    ListenerRegistration locationListener;

    public FireStoreDAO(MainActivity contextParent) {
        this.db = FirebaseFirestore.getInstance();
        this.contextParent = contextParent;
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
                            for (QueryDocumentSnapshot document : snapshot) {
                                if (!listCarPlate.contains(document.getData().get("carPlate").toString())) {
                                    listCarPlate.add(document.getData().get("carPlate").toString());
                                }
                            }
                            contextParent.setListCarPlate(listCarPlate);
                        }
                    }
                });
    }
    public void listenChangeLocationListener() {
        locationListener = query.whereEqualTo("carPlate", contextParent.carPlate).orderBy("time", Query.Direction.DESCENDING)
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
                    Location location = new Location(
                            document.getData().get("carPlate").toString(),
                            Float.parseFloat(document.getData().get("bearing").toString()),
                            Float.parseFloat(document.getData().get("latitude").toString()),
                            Float.parseFloat(document.getData().get("longitude").toString()),
                            document.getData().get("geocode").toString(),
                            document.getData().get("time").toString()
                    );
                    contextParent.setLastLocation(location);
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
        listenChangeLocationListener();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(contextParent, "Okie, Finished", Toast.LENGTH_SHORT).show();
    }
}
