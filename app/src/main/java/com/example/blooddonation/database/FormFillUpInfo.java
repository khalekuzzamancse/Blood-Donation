package com.example.blooddonation.database;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FormFillUpInfo {
    CallbackStringList callback;
    List<String> list;
    FirebaseFirestore db;
    private static final String collectionName = "DistrictList";

    public FormFillUpInfo() {
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
    }

    OnCompleteListener<QuerySnapshot> callbackQS = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String info = document.getId();
                    //      info = document.toObject(String.class);
                    list.add(info);
                    //   Log.i("ReceivedData-FirebaseCustom", document.getId() + " => " + document.getData());
                }

                callback.receivedList(list);
                //Log.i("FETCHED_District", String.valueOf(districtList));
                //making the list empty or null so that existing data will remove

            }
        }
    };

    public void getDistricts(CallbackStringList callback) {
        this.callback = callback;
        CollectionReference cref = db.collection(collectionName);
        Task<QuerySnapshot> snapshotTask = cref.get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }


    public List<String> getBloodGroups() {
        List<String> list = new ArrayList<>();
        list.add("A+");
        list.add("A-");
        list.add("B+");
        list.add("B-");
        list.add("O+");
        list.add("O-");
        list.add("AB+");
        list.add("AB-");
        return list;
    }


}
