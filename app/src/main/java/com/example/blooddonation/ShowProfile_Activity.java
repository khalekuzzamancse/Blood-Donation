package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.blooddonation.database.CallbackUserProfile;
import com.example.blooddonation.database.FirebaseAuthCustom;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;

import java.util.Objects;

public class ShowProfile_Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView nameTV, emailTV, phoneTV, bloodGroupTV, genderTV, districtTV, subDistrictTV, ageTV;
    DomainUserInfo userInfo;
    private CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
        @Override
        public void getProfile(DomainUserInfo profile) {
            userInfo = profile;
            setProfile(profile);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_profile_activity);
        initialize();
        setToolbar();

        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);


    }


    private void setProfile(DomainUserInfo info) {
        nameTV.setText("Name :" + info.Name);
        emailTV.setText("Email :" + info.Email);
        phoneTV.setText("Phone No :" + info.PhoneNumber);
        //if the user is a donor,then the following information are available
        if (info.BloodGroup != null) {
            bloodGroupTV.setText("Blood Group :" + info.BloodGroup);
            ageTV.setText("Age :" + info.Age);
            genderTV.setText("Gender :" + info.Gender);
            districtTV.setText("District :" + info.District);
            subDistrictTV.setText("SubDistrict :" + info.SubDistrict);
        }


    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
    }

    private void initialize() {
        userInfo = new DomainUserInfo();
        toolbar = findViewById(R.id.ShowProfileToolbar);
        nameTV = findViewById(R.id.name_TV);
        emailTV = findViewById(R.id.email_TV);
        phoneTV = findViewById(R.id.phone_TV);
        genderTV = findViewById(R.id.gender_TV);
        bloodGroupTV = findViewById(R.id.blood_group_TV);
        ageTV = findViewById(R.id.age_TV);
        districtTV = findViewById(R.id.district_TV);
        subDistrictTV = findViewById(R.id.sub_district_TV);
    }

    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_profile, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Activity_ProifleMenuEditProfile)
            startActivity(new Intent(this, EditProfileActivity.class));
        else if (id == R.id.Activity_ProifleMenuDonatDateUpdate)
            startActivity(new Intent(this, Activity_UpdateDonatDate.class));

        return super.onOptionsItemSelected(item);
    }
}
