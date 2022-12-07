package com.example.blooddonation.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BloodInfo {
    FirebaseFirestore db;
    Callback callbackCustom;
    List<DomainUserInfo> list;
    private CallbackNoOfDoc callbackNoOfDoc, callbackTot_Donor;
    private static final String collectionName = "UserInfo";

    OnCompleteListener<QuerySnapshot> callbackQS = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    //checking 3 month is completed or not to donate
                    //if today date is greater than 3  month from his donate date
                    //then we add him to the list
                    //other wise we do not show him in the search result
                    if (checkDonateDate(document))
                    {
                        DomainUserInfo info = new DomainUserInfo();
                        info = document.toObject(DomainUserInfo.class);
                        list.add(info);
                    }

                    //   Log.i("ReceivedData-FirebaseCustom", document.getId() + " => " + document.getData());
                }
                callbackCustom.receivedList(list);
                //Log.i("FETCHED_District", String.valueOf(districtList));
                //making the list empty or null so that existing data will remove

            }
        }
    };
    //
//    LocalDate date = LocalDate.now();
//    int m = date.getMonth().getValue();
//    int d = date.getDayOfMonth();
//    int y = date.getYear();
//    int todays = m * 30 + y * 365 + d;
//            Log.i("NEXTDAY", String.valueOf(days-todays));
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
        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("isDonor", "true").get();
        snapshotTask.addOnCompleteListener(callbackQSTot_Donor);
    }

    private boolean checkDonateDate(QueryDocumentSnapshot document) {
        String futureDate = (String) document.get("nextDonateDate");
        if (futureDate != null) {
            LocalDate todayDate = LocalDate.now();
            LocalDate nextDonateDate = LocalDate.parse(futureDate);
            if (todayDate.isAfter(nextDonateDate))
                return true;
            else
                return false;
        }

        return true;
    }


}