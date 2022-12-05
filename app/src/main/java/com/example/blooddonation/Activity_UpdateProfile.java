package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Activity_UpdateProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_scrollable);
        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile");
        getProfile();
        setGender();
        setBloodGroup();
        setLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void setGender() {
        List<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, genderList);
        AutoCompleteTextView gender = findViewById(R.id.Activity_UpdateProfile_TextInputLayout_AutoCompleteTextView_Gender);
        gender.setAdapter(adapter);
        gender.setText(MainActivity.model.getSignUserInfo().getValue().get("Gender"), false);
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();

            }
        });
    }


    private void setLocation() {
        List<String> districtList = new ArrayList<>();
        districtList = MainActivity.districtListModel.getDistrictList().getValue();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_UpdateProfile.this, R.layout.layout_drop_down_menu_single_item, districtList);
        AutoCompleteTextView d =
                findViewById(R.id.Activity_UpdateProfile_TextInputLayout_AutoCompleteTextView_District);
        d.setAdapter(adapter);
        d.setText(MainActivity.model.getSignUserInfo().getValue().get("District"), false);

        d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                Log.i("Clickeed", s);
                setSubDistrict(s);
            }
        });

    }

    private void setSubDistrict(String s) {
        List<String> subDistrictList = new ArrayList<>();
        subDistrictList = MainActivity.districtListModel.getDistrictListHashMap().getValue().get(s);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_UpdateProfile.this, R.layout.layout_drop_down_menu_single_item, subDistrictList);
        AutoCompleteTextView d = findViewById(R.id.Activity_UpdateProfile_TextInputLayout_AutoCompleteTextView_SubDistrict);
        d.setAdapter(adapter);
        d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();


            }
        });

    }


    private void setBloodGroup() {
        List<String> BloodGroupList = new ArrayList<>();
        BloodGroupList.add("A+");
        BloodGroupList.add("A-");
        BloodGroupList.add("B+");
        BloodGroupList.add("B-");
        BloodGroupList.add("O+");
        BloodGroupList.add("O-");
        BloodGroupList.add("AB+");
        BloodGroupList.add("AB-");
//
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, BloodGroupList);
        AutoCompleteTextView bloodGroup = findViewById(R.id.Activity_UpdateProfile__TextInputLayout_AutoCompleteTextView_BloodGroup);
        bloodGroup.setAdapter(adapter);
        //bloodGroup.setText("heloo",false);
        bloodGroup.setText(MainActivity.model.getSignUserInfo().getValue().get("BloodGroup"), false);

        bloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getItemAtPosition(position).toString();
                Log.i("Clickeed", s);

            }
        });
    }

    private void getProfile() {

        String name = MainActivity.model.getSignUserInfo().getValue().get("Name");
        String email = MainActivity.model.getSignUserInfo().getValue().get("Email");
        String phone = MainActivity.model.getSignUserInfo().getValue().get("PhoneNumber");
        String dis = MainActivity.model.getSignUserInfo().getValue().get("District");
        String subDis = MainActivity.model.getSignUserInfo().getValue().get("SubDistrict");
        String age = MainActivity.model.getSignUserInfo().getValue().get("Age");
        String gender = MainActivity.model.getSignUserInfo().getValue().get("Gender");
        String bloodGroup = MainActivity.model.getSignUserInfo().getValue().get("BloodGroup");
        String password = MainActivity.model.getSignUserInfo().getValue().get("Password");
        Activity_Account_Delete.get_PassWord = password;
        EditText tName = findViewById(R.id.Activity_UpdateProfile_TextInputLayout_EditText_Name);
        if (tName != null)
            tName.setText(name);
        EditText tEmail = findViewById(R.id.Activity_UpdateProfile_TextInputLayout_EditText_Email);
        if (tEmail != null)
            tEmail.setText(email);
        EditText tPhone = findViewById(R.id.Activity_UpdateProfile_TextInputLayout_EditText_Phone);
        if (tPhone != null)
            tPhone.setText(phone);
        EditText tAge = findViewById(R.id.Activity_UpdateProfile_TextInputLayout_EditText_Age);
        tAge.setText(age);


    }


}