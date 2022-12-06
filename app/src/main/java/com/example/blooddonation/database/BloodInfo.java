package com.example.blooddonation.database;

import androidx.annotation.NonNull;

import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BloodInfo {
    FirebaseFirestore db;
    Callback callbackCustom;
    List<DomainUserInfo> list;
    private CallbackNoOfDoc callbackNoOfDoc,callbackTot_Donor;
    private static final String collectionName = "UserInfo";

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
    OnCompleteListener<QuerySnapshot> callbackCollectionSize = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                int len = task.getResult().size();
            //  Log.i("ReceivedData-FirebaseLen", String.valueOf(len));
                callbackNoOfDoc.receivedSize(len);
            }
        }
    };
    OnCompleteListener<QuerySnapshot> callbackQSTot_Donor = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                int len = task.getResult().size();
                //  Log.i("ReceivedData-FirebaseLen", String.valueOf(len));
                callbackTot_Donor.receivedSize(len);
            }
        }
    };




    //<-----------Methods----->
    //<-----------Methods----->
    //<-----------Methods----->
    public BloodInfo() {

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




    /// call back for aggerage function
    public void getTotalUser(CallbackNoOfDoc callbackNoOfDoc) {
        this.callbackNoOfDoc = callbackNoOfDoc;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cref = db.collection(collectionName);
        Task<QuerySnapshot> snapshotTask = cref.get();
        snapshotTask.addOnCompleteListener(callbackCollectionSize);
    }
    public void getTotalDonor(CallbackNoOfDoc callbackTot_Donor) {
        this.callbackTot_Donor = callbackTot_Donor;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cref = db.collection(collectionName);
        Task<QuerySnapshot> snapshotTask = cref.  whereEqualTo("isDonor", "true").get();
        snapshotTask.addOnCompleteListener(callbackQSTot_Donor);
    }



}