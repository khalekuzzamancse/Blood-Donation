package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.blooddonation.database.Callback;
import com.example.blooddonation.database.FirebaseCustom;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Datatest_Activity extends AppCompatActivity {
    private List<DomainUserInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.datatest_activity);
        list = new ArrayList<>();

        Callback callback = new Callback() {
            @Override
            public void receivedList(List<DomainUserInfo> List) {
                list = List;
                Log.i("ReceviedList", String.valueOf(list));
            }
        };
        FirebaseCustom db = new FirebaseCustom();

        db.getBloodGroupSubDisWiseList("O+", "Jashore", "Jessore Sadar", callback);
        db.getBloodGroupDisWiseList("O+", "Jashore", callback);
        db.getBloodGroupWiseList("O+", callback);
        db.getAllDonorList(callback);



    }
}