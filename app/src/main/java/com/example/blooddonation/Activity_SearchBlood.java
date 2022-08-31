package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_SearchBlood extends AppCompatActivity {

    List<String> subDistrictList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);
        Toolbar toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Blood");
        setBloodGroup();
        DistrictList();
        Button search=findViewById(R.id.Activity_SearchBlood_Button_Submit);
        search.setOnClickListener(view -> {
            EditText bloodGroup=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
          String blood=bloodGroup.getText().toString().trim();
            EditText Dis=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
            String dis=Dis.getText().toString().trim();
            EditText SubDis=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_SubDistrict);
            String subDis=SubDis.getText().toString().trim();
            Intent intent=new Intent(this,Acitivity_SearchResult_Recycler.class);
            intent.putExtra(Acitivity_SearchResult_Recycler.EXTRA_bloodGroup,blood);
            intent.putExtra(Acitivity_SearchResult_Recycler.EXTRA_District,dis);
            intent.putExtra(Acitivity_SearchResult_Recycler.EXTRA_SubDistrict,subDis);
            startActivity(intent);


           // AllUserInfoList();

        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_for_non_home_activity_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.NonHomeActivity_MenuItem_SearchBlood)
        {
            Intent intent=new Intent(this,Activity_SearchBlood.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void setBloodGroup()
    {
        List<String> BloodGroupList=new ArrayList<>();
        BloodGroupList.add("A+");
        BloodGroupList.add("A-");
        BloodGroupList.add("B+");
        BloodGroupList.add("B-");
        BloodGroupList.add("O+");
        BloodGroupList.add("O-");
        BloodGroupList.add("AB+");
        BloodGroupList.add("AB-");
//
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.layout_drop_down_menu_single_item,BloodGroupList);
        AutoCompleteTextView bloodGroup=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
        bloodGroup.setAdapter(adapter);

        bloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s=  parent.getItemAtPosition(position).toString();
                Log.i("Clickeed",s);

            }
        });
    }
//    private void setDistrictList()
//    {
//        List<String> BloodGroupList=new ArrayList<>();
//
//
//    }
//

    private void DistrictList()
    {
        List<String> districtList=new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DistrictList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Hello", document.getId() + " => " + document.getData());
                               // String name=(String)document.get("Name");
                                String name=(String)document.getId();
                                Log.i("District",name);
                                districtList.add(name);
                            }

                            ArrayAdapter<String> adapter=new ArrayAdapter<>(Activity_SearchBlood.this,R.layout.layout_drop_down_menu_single_item,districtList);
                            AutoCompleteTextView d=
                                    findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
                           d.setAdapter(adapter);

                           d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String s=  parent.getItemAtPosition(position).toString();
                                    Log.i("Clickeed",s);
                                    setSubDistrict(s);
                                }
                            });
                        }


                        else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    private void setSubDistrict(String s)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DistrictList")
                .document(s)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<String> subDistrictList = (List<String>) document.get("SubDistrict");
                        for (String it : subDistrictList) {
                            Log.d("Upozilla", it);
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<>(Activity_SearchBlood.this,R.layout.layout_drop_down_menu_single_item,subDistrictList);
                        AutoCompleteTextView d=
                                findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_SubDistrict);
                        d.setAdapter(adapter);

                        d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String s=  parent.getItemAtPosition(position).toString();
                                Log.i("Clickeed",s);


                            }
                        });



                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }



    private void AllUserInfoList()
    {
        List<HashMap<String,Object>> AllUserInfoList=new ArrayList<>();
        List<HashMap<String,Object>> DistrictWiseUserInfoList=new ArrayList<>();
        List<HashMap<String,Object>> subDistrictWiseUserInfoList=new ArrayList<>();
        EditText bloodGroup=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
        String blood=bloodGroup.getText().toString().trim();
        EditText Dis=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
        String dis=Dis.getText().toString().trim();
        EditText SubDis=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_SubDistrict);
        String subDis=SubDis.getText().toString().trim();
        Log.d("GettedAgain",blood+"->"+dis+"->"+subDis);

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

                        for (HashMap<String, Object> map : subDistrictWiseUserInfoList) {
//                            for (String key : map.keySet()) {
//                                Log.i("Displayed",key+":"+(String) map.get(key));
//
//                            }
                            Log.i("Displayed", (String) map.get("Name")+":"+(String) map.get("BloodGroup")+":"+(String) map.get("District")+":"+(String) map.get("SubDistrict"));

                        }

                    }
                });



    }
}