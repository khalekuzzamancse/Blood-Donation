package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

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
import android.widget.Toast;

import com.example.blooddonation.database.CallbackResult;
import com.example.blooddonation.database.CallbackStringList;
import com.example.blooddonation.database.CallbackUserProfile;
import com.example.blooddonation.database.FirebaseAuthCustom;
import com.example.blooddonation.database.FormFillUpInfo;
import com.example.blooddonation.database.WritingDocument;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
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
            phoneET.setText(info.PhoneNumber);
            //if the user is a donor then it have age other wise age is null
            // because non donor use not store the age information
            if(info.Age!=null)
                ageET.setText(info.Age);

        }
    };

    //
   private CallbackResult callbackResult = new CallbackResult() {

        @Override
        public void isSuccess(boolean response) {
          //  progressBar.setVisibility(View.INVISIBLE);
            if (response)
               // showSnackBar("Updated Successfully");
                Toast.makeText(EditProfileActivity.this, "Updated Successfully",
                        Toast.LENGTH_LONG).show();
            else
               // showSnackBar("Failed");
                Toast.makeText(EditProfileActivity.this, "Failed",
                        Toast.LENGTH_LONG).show();
//            Intent intent = getIntent();
//            finish();
//            startActivity(intent);
//            Intent i = new Intent(Become_Donor_Activity.this, MainActivity.class);
//            startActivity(i);
            onBackPressed();
        }
    };
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_scrollable);

        initialize();
        setToolbar();
        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);

        submitBTN.setOnClickListener(view -> {
            updateInfo();

        });
        cancelBTN.setOnClickListener(view->{
            onBackPressed();
        });

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
    private void updateInfo() {
        HashMap<String, Object> data = new HashMap<>();
        String name=nameET.getText().toString().trim();
        data.put("Name",name);
        String phone=phoneET.getText().toString().trim();
        data.put("PhoneNumber",phone);
        String Age = ageET.getText().toString().trim();
        data.put("Age", Age);
        String gender = genderACTV.getText().toString().trim();
        data.put("Gender", gender);
        String bloodGroup = bloodGroupACTV.getText().toString().trim();
        data.put("BloodGroup", bloodGroup);
        String dis = districtACTV.getText().toString().trim();
        data.put("District", dis);
        String subDis = subDistrictACTV.getText().toString().trim();
        data.put("SubDistrict", subDis);
        data.put("isDonor", "true");
        WritingDocument document = new WritingDocument();
        document.updateDocument(info.Email, data, callbackResult);
         Log.i("DataGot", String.valueOf(data));
    }
    void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(submitBTN, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }
    ////replace the back button with navigationUp because
    //1.Main activity read the data from the database
    //2.based on user data some menu item will be hide
    //3.if we use navigation up then main activity will be recreated
    //4.as a result we got the updated data
    //5.according to updated data menu item list will be updated
    @Override
    public void onBackPressed() {
        onNavigateUp();
    }

}