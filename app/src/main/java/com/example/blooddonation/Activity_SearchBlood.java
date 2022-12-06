package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.blooddonation.database.CallbackStringList;
import com.example.blooddonation.database.FormFillUpInfo;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class Activity_SearchBlood extends AppCompatActivity {
    TextInputLayout bloodGroupLayout;

    ProgressBar progressBar;
    AutoCompleteTextView bloodGroupACTV, districtACTV, subDistrictACTV;
    Button clear, search;
    ArrayAdapter<String> bloodGroupAdapter, districtAdapter, subDistrictAdapter;
    FormFillUpInfo fillUpInfo;
    Toolbar toolbar;
    // String bloodGroupStr="",districtStr="",subDistrictStr="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);
        initialize();
        setToolbar();
        setBloodGroup();
        //  setLocation();
        setDistrict();


        //check if the blood group filed is empty or not
        //if the there are any error  message then the checkedBloodGroupField() will clear or do not clear the message
        checkBloodGroupField();
        search.setOnClickListener(view -> {


            //  p.setVisibility(View.VISIBLE);

            String blood = bloodGroupACTV.getText().toString().trim();
            String dis = districtACTV.getText().toString().trim();
            if (dis.equals(""))
                dis = "null";
            String subDis = subDistrictACTV.getText().toString().trim();
            if (subDis.equals(""))
                subDis = "null";

            Intent intent = new Intent(this, Activity_AllUserInfoList.class);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_bloodGroup, blood);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_District, dis);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_SubDistrict, subDis);
            intent.putExtra(Activity_AllUserInfoList.Extra_ComingFrom, "SearchBlood");
            //  p.setVisibility(View.INVISIBLE);
            //if the user not choosen a bloodGroup then we do not show the list

            if (!bloodGroupACTV.getText().toString().isEmpty())
                startActivity(intent);
            else {
                //if user not chosen a bloodGroup but clicked the submit button.
                //then show the error message
                bloodGroupLayout.setError("Please,Choose a blood Group");
            }


        });


    }

    // <--------------onCreate method end---->
    // <-------- method for setting blood group on the drop down menu is start-->

    private void setBloodGroup() {
        bloodGroupAdapter = new ArrayAdapter<>(this,
                R.layout.layout_drop_down_menu_single_item, fillUpInfo.getBloodGroups());
        bloodGroupACTV.setAdapter(bloodGroupAdapter);
    }
    // <-------- method for setting blood group on the drop down menu is end-->


    // sub district setting call back
    CallbackStringList cbSub = new CallbackStringList() {
        @Override
        public void receivedList(List<String> list) {
            subDistrictAdapter = new ArrayAdapter<>(Activity_SearchBlood.this,
                    R.layout.layout_drop_down_menu_single_item, list);
            subDistrictACTV.setAdapter(subDistrictAdapter);

        }
    };

    private void initialize() {
        fillUpInfo = new FormFillUpInfo();
        clear = findViewById(R.id.Activity_SearchBlood_Button_Cancel);
        bloodGroupACTV = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
        bloodGroupLayout = findViewById(R.id.Activity_SearchBlood_TextInputLayout_BloodGroup);
        search = findViewById(R.id.Activity_SearchBlood_Button_Submit);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        progressBar = findViewById(R.id.ActivitySearch_ProgressBar);
        districtACTV = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
        subDistrictACTV = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_SubDistrict);
        bloodGroupACTV = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
    }


    //<--------Toolbar setting logic starting--->
    private void setToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Blood");

    }
    //<--------Toolbar setting logic ended--->

    private void setDistrict() {
        CallbackStringList cb = list -> {
            districtAdapter = new ArrayAdapter<>(Activity_SearchBlood.this,
                    R.layout.layout_drop_down_menu_single_item, list);
            districtACTV.setAdapter(districtAdapter);
            districtACTV.setOnItemClickListener((parent, view, position, id) -> {
                String s = parent.getItemAtPosition(position).toString();
                fillUpInfo.getSubDistricts(s, cbSub);

            });

        };
        fillUpInfo.getDistricts(cb);
    }


    private void checkBloodGroupField() {
        //since the text has changed so the bloodGroupField is not empty
        //so we removing the error message.
        bloodGroupACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //keep empty,beacase we remove the error message after text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //keep empty,beacase we remove the error message after text changed
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //removing the error message
                bloodGroupLayout.setError(null);
            }
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
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}