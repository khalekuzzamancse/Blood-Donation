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
import android.widget.Button;
import android.widget.EditText;

import com.example.blooddonation.database.CallbackStringList;
import com.example.blooddonation.database.CallbackUserProfile;
import com.example.blooddonation.database.FirebaseAuthCustom;
import com.example.blooddonation.database.FormFillUpInfo;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    AutoCompleteTextView genderACTV, bloodGroupACTV, districtACTV, subDistrictACTV;
    Button submitBTN, cancelBTN;
    EditText nameET, phoneET, emailET, ageET;
    Toolbar toolbar;
    DomainUserInfo info;
    ArrayAdapter<String> genderAdapter, bloodGroupAdapter, districtAdapter, subDistrictAdapter;
    FormFillUpInfo fillUpInfo;

    //after reading the data we will update the view ....
    private CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
        @Override
        public void getProfile(DomainUserInfo profile) {
            info = profile;
            setGender();
            setBloodGroup();
            setDistrict();
            nameET.setText(info.Name);
            emailET.setText(info.Email);
            phoneET.setText(info.PhoneNumber);
            //if the user is a donor then it have age other wise age is null
            // because non donor use not store the age information
            if(info.Age!=null)
                ageET.setText(info.Age);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_scrollable);

        initialize();
        setToolbar();
        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);

    }


    private void setGender() {
        String[] list = {"Male", "Female", "Other"};
        genderAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        genderACTV.setAdapter(genderAdapter);
        //if the user is donor then his
        //gender,age,district,district,blood group info is available(not null)
        if (info.Gender != null)
            genderACTV.setText(info.Gender, false);
    }

    private void setBloodGroup() {
        String[] list = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        bloodGroupAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        bloodGroupACTV.setAdapter(bloodGroupAdapter);
        if (info.BloodGroup != null)
            bloodGroupACTV.setText(info.BloodGroup, false);
    }

    private CallbackStringList cbSub = new CallbackStringList() {
        @Override
        public void receivedList(List<String> list) {
            subDistrictAdapter = new ArrayAdapter<>(EditProfileActivity.this,
                    R.layout.layout_drop_down_menu_single_item, list);
            subDistrictACTV.setAdapter(subDistrictAdapter);
            if (info.SubDistrict != null)
                subDistrictACTV.setText(info.SubDistrict, false);

        }
    };

    private void setDistrict() {
        CallbackStringList cb = list -> {
            districtAdapter = new ArrayAdapter<>(EditProfileActivity.this,
                    R.layout.layout_drop_down_menu_single_item, list);
            districtACTV.setAdapter(districtAdapter);

            if (info.District != null)
                districtACTV.setText(info.District, false);
            districtACTV.setOnItemClickListener((parent, view, position, id) -> {
                String s = parent.getItemAtPosition(position).toString();
                fillUpInfo.getSubDistricts(s, cbSub);

            });

        };
        fillUpInfo.getDistricts(cb);
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



    private void initialize() {
        //ET=EditText
        //TV=TextView
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        info = new DomainUserInfo();
        fillUpInfo = new FormFillUpInfo();
        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phone_noET);
        emailET = findViewById(R.id.emailET);
        ageET = findViewById(R.id.ageET);
        cancelBTN = findViewById(R.id.cancelBTN);
        bloodGroupACTV = findViewById(R.id.bloodGroupACTV);
        submitBTN = findViewById(R.id.submitBTN);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        //  progressBar = findViewById(R.id.progressBar);
        districtACTV = findViewById(R.id.districtACTV);
        subDistrictACTV = findViewById(R.id.subDistrictACTV);
        bloodGroupACTV = findViewById(R.id.bloodGroupACTV);
        genderACTV = findViewById(R.id.genderACTV);

    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile");
    }

}