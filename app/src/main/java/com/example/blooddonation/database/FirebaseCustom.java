package com.example.blooddonation.database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseCustom {
    FirebaseFirestore db;
    Callback callbackCustom;
    List<DomainUserInfo> list;
    String userType = "";
    boolean increment=false;

    OnCompleteListener<QuerySnapshot> callbackQS = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    DomainUserInfo info = new DomainUserInfo();
                    info = document.toObject(DomainUserInfo.class);
                    list.add(info);
                    //   Log.i("ReceivedData-FirebaseCustom", document.getId() + " => " + document.getData());
                }
                callbackCustom.receivedList(list);
                //Log.i("FETCHED_District", String.valueOf(districtList));
                //making the list empty or null so that existing data will remove

            }
        }
    };
    EventListener<DocumentSnapshot> callbackDocSnapShot = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {

            if (document != null && document.exists()) {
                if (userType.equals("User")&&!increment) {

                    String num = (String) document.get("TotalUser");
                    Log.i("UserNUM-Or", num);
                    num = String.valueOf(Integer.parseInt(num) + 1);
                    Log.i("UserNUM-In", num);
                    addDocument(num);
                    increment=true;

                }

                Log.i("Fetched-Doc ", String.valueOf(document.getData()));
            }
        }
    };

    //<-----------Methods----->
    //<-----------Methods----->
    //<-----------Methods----->
    public FirebaseCustom() {

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
    }

    public void getBloodGroupSubDisWiseList(String bloodGroup, String district, String subDistrict, Callback callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cref = db.collection("UserInfo");
        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("BloodGroup", bloodGroup)
                .whereEqualTo("District", district)
                .whereEqualTo("SubDistrict", subDistrict)
                .get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

    public void getBloodGroupDisWiseList(String bloodGroup, String district, Callback callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cref = db.collection("UserInfo");
        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("BloodGroup", bloodGroup)
                .whereEqualTo("District", district)
                .get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

    public void getBloodGroupWiseList(String bloodGroup, Callback callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cref = db.collection("UserInfo");
        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("BloodGroup", bloodGroup)
                .get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

    public void getAllDonorList(Callback callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cref = db.collection("UserInfo");
        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("isDonor", "true")
                .get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

    public void getDistrictWiseDonorList(String district, Callback callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cref = db.collection("UserInfo");
        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("isDonor", "true")
                .whereEqualTo("District", district)
                .get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

    public void getSubDistrictWiseDonorList(String district, String subDistrict, Callback callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cref = db.collection("UserInfo");
        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("isDonor", "true")
                .whereEqualTo("District", district)
                .whereEqualTo("SubDistrict", subDistrict)
                .get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

    public void incrementUserNumber(String userType) {
        this.userType = userType;
        CollectionReference cref = db.collection("UserInfo");
        DocumentReference docRef = cref.document("userNumbers");
        docRef.addSnapshotListener(callbackDocSnapShot);

    }

    public void addDocument(String num) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("TotalUser", num);
        Log.i("UserNUM-Inc", String.valueOf(data));
        CollectionReference cref = db.collection("UserInfo");
        DocumentReference docRef = cref.document("userNumbers");
        docRef.set(data, SetOptions.merge());

    }


}
