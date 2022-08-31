package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Acitivity_SearchResult_Recycler extends AppCompatActivity {
    public static final String EXTRA_bloodGroup="BloodGroup";
    public static final String EXTRA_District="District";
    public static final String EXTRA_SubDistrict="SubDistrict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_search_result_recycler);
        AllUserInfoList();
//        List<DataTypeForRecyclerView_ActivitySearchResult> list=new ArrayList<>();
//
//        DataTypeForRecyclerView_ActivitySearchResult obj=new DataTypeForRecyclerView_ActivitySearchResult();
//        obj.Name="NameA";
//        obj.Email="EmailB";
//        obj.PhoneNumber="089851";
//        obj.BloodGroup="A+";
//        obj.District="Rangpur";
//        obj.SubDistrict="Ganagachara";
//        list.add(obj);
//
//
//        AdapterForRecyclerViewLoggedIn adapter=new AdapterForRecyclerViewLoggedIn(this,list);
//        RecyclerView r=findViewById(R.id.Activity_SearchResult_Recycler);
//        r.setLayoutManager(new LinearLayoutManager(this));
//        r.setAdapter(adapter);




    }


    private void AllUserInfoList()
    {
        List<HashMap<String,Object>> AllUserInfoList=new ArrayList<>();
        List<HashMap<String,Object>> DistrictWiseUserInfoList=new ArrayList<>();
        List<HashMap<String,Object>> subDistrictWiseUserInfoList=new ArrayList<>();

        Intent i=getIntent();
        String blood=i.getStringExtra(EXTRA_bloodGroup);
        String dis=i.getStringExtra(EXTRA_District);
        String subDis=i.getStringExtra(EXTRA_SubDistrict);
        Log.i("Alhamuddilha,WE are",blood+"->"+dis+"->"+subDis);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserInfo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                String name=(String)document.get("Name");
                                //  String Username=(String)document.get("UserName");
                                String phoneNumber=(String)document.get("PhoneNumber");
                                String email=(String)document.get("Email");
                                String bloodGroup=(String)document.get("BloodGroup");
                                String gender=(String)document.get("Gender");
                                String district=(String)document.get("District");
                                String subDistrict=(String)document.get("SubDistrict");
                                String age=(String)document.get("Age");
                                Log.i("User:",name+"->"+phoneNumber+"->"+email+"->"+bloodGroup+"->"+gender+"->"+district+"->"+subDistrict+"->"+age);
                                if(blood.equals(bloodGroup)&&dis.equals(district)&&subDis.equals(subDistrict))
                                {
                                    Log.i("Alhamdulliah,Founded:",name+"->"+phoneNumber+"->"+email+"->"+bloodGroup+"->"+gender+"->"+district+"->"+subDistrict+"->"+age);
                                    HashMap<String,Object>dataSubDistrict=new HashMap<>();
                                    dataSubDistrict.put("Name",name);
                                    dataSubDistrict.put("PhoneNumber",phoneNumber);
                                    dataSubDistrict.put("Email",email);
                                    dataSubDistrict.put("BloodGroup",bloodGroup);
                                    dataSubDistrict.put("Gender",gender);
                                    dataSubDistrict.put("District",district);
                                    dataSubDistrict.put("SubDistrict",subDistrict);
                                    dataSubDistrict.put("Age",age);
                                    subDistrictWiseUserInfoList.add(dataSubDistrict);
                                }


                                //String name=(String)document.getId();
//                                Log.i("User:",name);
//                                userInfoList.add()
                            }

                        }

                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        Log.i("Founded Size", String.valueOf(subDistrictWiseUserInfoList.size()));


                        List<DataTypeForRecyclerView_ActivitySearchResult> list=new ArrayList<>();


                        AdapterForRecyclerViewLoggedIn adapter=new AdapterForRecyclerViewLoggedIn(Acitivity_SearchResult_Recycler.this,list);
                        RecyclerView r=findViewById(R.id.Activity_SearchResult_Recycler);
                        r.setLayoutManager(new LinearLayoutManager(Acitivity_SearchResult_Recycler.this));
                        r.setAdapter(adapter);

                        for (HashMap<String, Object> map : subDistrictWiseUserInfoList) {
//                            for (String key : map.keySet()) {
//                                Log.i("Displayed",key+":"+(String) map.get(key));
//
//                            }
                            Log.i("Displayed", (String) map.get("Name")+":"+(String) map.get("BloodGroup")+":"+(String) map.get("District")+":"+(String) map.get("SubDistrict"));
                            DataTypeForRecyclerView_ActivitySearchResult obj=new DataTypeForRecyclerView_ActivitySearchResult();
                            obj.Name=(String) map.get("Name");
                            obj.Email=(String) map.get("Email");
                            obj.PhoneNumber=(String) map.get("PhoneNumber");
                            obj.BloodGroup=(String) map.get("BloodGroup");
                            obj.District=(String) map.get("District");
                            obj.SubDistrict=(String) map.get("SubDistrict");
                            list.add(obj);


                        }

                    }
                });



    }
}