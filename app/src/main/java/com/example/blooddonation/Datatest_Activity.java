package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.blooddonation.database.Callback;
import com.example.blooddonation.database.BloodInfo;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;

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
        BloodInfo db = new BloodInfo();

        db.getBloodGroupSubDisWiseList("O+", "Jashore", "Jessore Sadar", callback);
        db.getBloodGroupDisWiseList("O+", "Jashore", callback);
        db.getBloodGroupWiseList("O+", callback);
        db.getAllDonorList(callback);



    }
}