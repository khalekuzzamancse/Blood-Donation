package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Acitivity_SearchResult_Recycler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_search_result_recycler);
        List<DataTypeForRecyclerView_ActivitySearchResult> list=new ArrayList<>();

        DataTypeForRecyclerView_ActivitySearchResult obj=new DataTypeForRecyclerView_ActivitySearchResult();
        obj.Name="NameA";
        obj.Email="EmailB";
        obj.PhoneNumber="089851";
        obj.BloodGroup="A+";
        obj.District="Rangpur";
        obj.SubDistrict="Ganagachara";
        list.add(obj);


        AdapterForRecyclerViewLoggedIn adapter=new AdapterForRecyclerViewLoggedIn(this,list);
        RecyclerView r=findViewById(R.id.Activity_SearchResult_Recycler);
        r.setLayoutManager(new LinearLayoutManager(this));
        r.setAdapter(adapter);

    }
}