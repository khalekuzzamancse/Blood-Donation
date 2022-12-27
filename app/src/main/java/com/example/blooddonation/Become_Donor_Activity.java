package com.example.blooddonation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.blooddonation.database.CallbackResult;
import com.example.blooddonation.database.CallbackStringList;
import com.example.blooddonation.database.FirebaseAuthCustom;
import com.example.blooddonation.database.FormFillUpInfo;
import com.example.blooddonation.database.WritingDocument;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Become_Donor_Activity extends AppCompatActivity {

    AutoCompleteTextView genderACTV, bloodGroupACTV, districtACTV, subDistrictACTV;
    Button submitBTN, cancelBTN;
    EditText ageET;
    ArrayAdapter<String> genderAdapter, bloodGroupAdapter, districtAdapter, subDistrictAdapter;
    FormFillUpInfo fillUpInfo;
    Toolbar toolbar;
    Button currentLocation;

    ProgressBar progressBar;
    //
    CallbackResult callbackResult = new CallbackResult() {

        @Override
        public void isSuccess(boolean response) {
            progressBar.setVisibility(View.INVISIBLE);
            if (response)
                showSnackBar("Updated Successfully");
            else
                showSnackBar("Failed");
            //   Intent intent = getIntent();
            finish();
            Intent i = new Intent(Become_Donor_Activity.this, MainActivity.class);
            startActivity(i);
        }
    };
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_donar_scrollable);
        initialize();
        setToolbar();
        setGender();
        setBloodGroup();
        setDistrict();

        Button submit = findViewById(R.id.submitBTN);
        submit.setOnClickListener(view -> {
            ProgressBar p = findViewById(R.id.progressBar);
            p.setVisibility(View.VISIBLE);
            AddDonorInfo();

        });


        currentLocation.setOnClickListener(view -> {
            getCurrentLocation();

        });


    }
    // <--------------onCreate method end---->
    // <-------- method for setting blood group on the drop down menu is start-->
    //<----------------------------------------->
    //<----------------------------------------->
    //<----------------------------------------->
    //<----------------------------------------->
    //<----------------------------------------->
    //<----------------------------------------->

    private void setGender() {
        String[] list = {"Male", "Female", "Other"};
        genderAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        genderACTV.setAdapter(genderAdapter);
    }

    private void setBloodGroup() {
        String[] list = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        bloodGroupAdapter = new ArrayAdapter<>(this, R.layout.layout_drop_down_menu_single_item, list);
        bloodGroupACTV.setAdapter(bloodGroupAdapter);
    }

    // <--sub district setting call back starting --->
    CallbackStringList cbSub = new CallbackStringList() {
        @Override
        public void receivedList(List<String> list) {
            subDistrictAdapter = new ArrayAdapter<>(Become_Donor_Activity.this,
                    R.layout.layout_drop_down_menu_single_item, list);
            subDistrictACTV.setAdapter(subDistrictAdapter);


        }
    };
    // <--sub district setting call back ended --->

    private void setDistrict() {
        CallbackStringList cb = list -> {
            districtAdapter = new ArrayAdapter<>(Become_Donor_Activity.this,
                    R.layout.layout_drop_down_menu_single_item, list);
            districtACTV.setAdapter(districtAdapter);
            districtACTV.setOnItemClickListener((parent, view, position, id) -> {
                String s = parent.getItemAtPosition(position).toString();
                fillUpInfo.getSubDistricts(s, cbSub);

            });

        };
        fillUpInfo.getDistricts(cb);
    }

    private void AddDonorInfo() {
        HashMap<String, Object> data = new HashMap<>();
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
        document.updateDocument(new FirebaseAuthCustom().getUserEmail(), data, callbackResult);
        // Log.i("DataGot", String.valueOf(data));
    }

    private void initialize() {

        fillUpInfo = new FormFillUpInfo();
        ageET = findViewById(R.id.ageET);
        cancelBTN = findViewById(R.id.cancelBTN);
        bloodGroupACTV = findViewById(R.id.bloodGroupACTV);
        submitBTN = findViewById(R.id.submitBTN);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        progressBar = findViewById(R.id.progressBar);
        districtACTV = findViewById(R.id.districtACTV);
        subDistrictACTV = findViewById(R.id.subDistrictACTV);
        bloodGroupACTV = findViewById(R.id.bloodGroupACTV);
        genderACTV = findViewById(R.id.genderACTV);
        currentLocation = findViewById(R.id.currentLocation);
    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Become Donor");

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_for_non_home_activity_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(submitBTN, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }

    private void getCurrentLocation() {

        FusedLocationProviderClient a = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        a.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
//                            LatLng s = new LatLng(location.getLatitude(), location.getLongitude());
//                            mMap.addMarker(new MarkerOptions().position(s).title(""));
//                            mMap.moveCamera(CameraUpdateFactory.newLatLng(s));
                            //   Log.i("LOcationaaa",location.);

                            Geocoder geocoder = new Geocoder(Become_Donor_Activity.this, Locale.getDefault());
                            List<Address> addresses = null;
                            try {

                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                Log.i("FoundLocationB", String.valueOf(addresses.get(0).getLatitude())
                                        + "," + addresses.get(0).getLongitude());
                               addLocation(String.valueOf(addresses.get(0).getLatitude()),String.valueOf(addresses.get(0).getLongitude()));


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
    }

    private void addLocation(String latitude, String longitude) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("latitude", latitude);
        data.put("longitude", longitude);
        WritingDocument document = new WritingDocument();
        document.updateDocument(new FirebaseAuthCustom().getUserEmail(), data);
        document.updateLocation(data);
        Intent intent = new Intent(Become_Donor_Activity.this, MapsActivity.class);
        startActivity(intent);

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