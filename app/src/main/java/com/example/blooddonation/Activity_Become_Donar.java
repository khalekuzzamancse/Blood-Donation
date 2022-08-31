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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Become_Donar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_donar_scrollable);
        Toolbar toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Become Donor");

            setGender();
            setBloodGroup();
            DistrictList();
        Button submit=findViewById(R.id.Activity_BecomeDonar_Button_Submit);
        submit.setOnClickListener(view -> {
            AddDonorInfo();

            Intent intent = getIntent();
            finish();
            startActivity(intent);
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);

        });
//this is comment


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
    private void setGender()
    {
        List<String> genderList=new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
//
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.layout_drop_down_menu_single_item,genderList);
        AutoCompleteTextView gender=findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_Gender);
        gender.setAdapter(adapter);

        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s=  parent.getItemAtPosition(position).toString();
                Log.i("Clickeed",s);

            }
        });
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
        AutoCompleteTextView bloodGroup=findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_BloodGroup);
        bloodGroup.setAdapter(adapter);

        bloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s=  parent.getItemAtPosition(position).toString();
                Log.i("Clickeed",s);

            }
        });
    }
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

                            ArrayAdapter<String> adapter=new ArrayAdapter<>(Activity_Become_Donar.this,R.layout.layout_drop_down_menu_single_item,districtList);
                            AutoCompleteTextView d=
                                    findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_District);
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
                                ArrayAdapter<String> adapter=new ArrayAdapter<>(Activity_Become_Donar.this,R.layout.layout_drop_down_menu_single_item,subDistrictList);
                                AutoCompleteTextView d=
                                        findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_SubDistrict);
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

    private void AddDonorInfo()
    {
        EditText age=findViewById(R.id.Activity_BecomeDonar_TextInputLayout_EditText_Age);
        String Age=age.getText().toString().trim();
        HashMap<String,Object>setAge=new HashMap<>();
        setAge.put("Age",Age);
        addANewField(setAge);
        EditText gender=findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_Gender);
        String Gender=gender.getText().toString().trim();
        HashMap<String,Object>setGender=new HashMap<>();
        setGender.put("Gender",Gender);
        addANewField(setGender);
        EditText bloodGroup=findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_BloodGroup);
        String blood=bloodGroup.getText().toString().trim();
        HashMap<String,Object>setBloodGroup=new HashMap<>();
        setBloodGroup.put("BloodGroup",blood);
        addANewField(setBloodGroup);
        EditText Dis=findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_District);
        String dis=Dis.getText().toString().trim();
        HashMap<String,Object>setDis=new HashMap<>();
        setDis.put("District",dis);
        addANewField(setDis);
        EditText SubDis=findViewById(R.id.Activity_BecomeDonar_TextInputLayout_AutoCompleteTextView_SubDistrict);
        String subDis=SubDis.getText().toString().trim();
        HashMap<String,Object>setSubDis=new HashMap<>();
        setSubDis.put("SubDistrict",subDis);
        addANewField(setSubDis);
        HashMap<String,Object>isDonor=new HashMap<>();
        isDonor.put("isDonor","true");
        addANewField(isDonor);

        Log.i("Alhadlliah",Age+" "+Gender+" "+blood+" "+dis+" "+subDis);
    }

    private void addANewField(HashMap<String,Object>Data)
    {
        FirebaseAuth mAuth; mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email=user.getEmail();
//        Data.put("SubDistrict","null");
//        Data.put("BloodGroup","null");
//        Data.put("Age","null");
//        Data.put("Gender","null");

        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("UserInfo")
                .document(email)
                .set(Data, SetOptions.merge())
                .addOnCompleteListener((Task<Void> task)->{
                    if(!task.isSuccessful())
                    {
                        Log.i("Failed to Added to database","Next,Inshallah");
                    }
                    else
                    {
                        Log.i("Added to Database","Alhadulliah");
                    }
                });

    }

}