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
import android.view.View;
import android.widget.AdapterView;
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

    List<String> subDistrictList = new ArrayList<>();
    ProgressBar progressBar;
    AutoCompleteTextView bloodGroupTV, districtTV;
    Button clear, search;
    AutoCompleteTextView bG;
    FormFillUpInfo fillUpInfo;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);
        initialize();
        setToolbar();
        setBloodGroup();
        //  setLocation();
        //    setDistrict();


        //check if the blood group filed is empty or not
        //if the there are any error  message then the checkedBloodGroupField() will clear or do not clear the message
        checkBloodGroupField();
        search.setOnClickListener(view -> {


            //  p.setVisibility(View.VISIBLE);

            String blood = bloodGroupTV.getText().toString().trim();


            EditText Dis = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
            String dis = Dis.getText().toString().trim();
            if (dis.equals(""))
                dis = "null";
            EditText SubDis = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_SubDistrict);
            String subDis = SubDis.getText().toString().trim();

            if (subDis.equals(""))
                subDis = "null";

            Intent intent = new Intent(this, Activity_AllUserInfoList.class);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_bloodGroup, blood);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_District, dis);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_SubDistrict, subDis);
            intent.putExtra(Activity_AllUserInfoList.Extra_ComingFrom, "SearchBlood");
            //  p.setVisibility(View.INVISIBLE);
            //if the user not choosen a bloodGroup then we do not show the list

            if (!bloodGroupTV.getText().toString().isEmpty())
                startActivity(intent);
            else {
                //if user not chosen a bloodGroup but clicked the submit button.
                //then show the error message
                bloodGroupLayout.setError("Please,Choose a blood Group");
            }


        });
        clear.setOnClickListener(view -> {
            bloodGroupTV.setText("null");
            Log.i("Ckssdkfjkfjsd", "kjfwf");
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

    private void setBloodGroup() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.layout_drop_down_menu_single_item, fillUpInfo.getBloodGroups());
        AutoCompleteTextView bloodGroup = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
        bloodGroup.setAdapter(adapter);

        bloodGroup.setOnItemClickListener((parent, view, position, id) -> {
            String s = parent.getItemAtPosition(position).toString();
            Log.i("Clickeed", s);

        });

    }

    private void initialize() {
        fillUpInfo = new FormFillUpInfo();
        clear = findViewById(R.id.Activity_SearchBlood_Button_Cancel);
        bloodGroupTV = findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
        bloodGroupLayout = findViewById(R.id.Activity_SearchBlood_TextInputLayout_BloodGroup);
        search = findViewById(R.id.Activity_SearchBlood_Button_Submit);
        toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        progressBar = findViewById(R.id.ActivitySearch_ProgressBar);
        AutoCompleteTextView districtTV =
                findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Blood");

    }

    private void setDistrict() {
        CallbackStringList cb = list -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_SearchBlood.this,
                    R.layout.layout_drop_down_menu_single_item, list);
            districtTV.setAdapter(adapter);

            districtTV.setOnItemClickListener((parent, view, position, id) -> {
                String s = parent.getItemAtPosition(position).toString();
                Log.i("Clickeed", s);

            });

        };
        fillUpInfo.getDistricts(cb);
    }

//    private void setBloodGroup()
//    {
//        List<String> BloodGroupList=new ArrayList<>();
//        BloodGroupList.add("A+");
//        BloodGroupList.add("A-");
//        BloodGroupList.add("B+");
//        BloodGroupList.add("B-");
//        BloodGroupList.add("O+");
//        BloodGroupList.add("O-");
//        BloodGroupList.add("AB+");
//        BloodGroupList.add("AB-");
////
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.layout_drop_down_menu_single_item,BloodGroupList);
//        AutoCompleteTextView bloodGroup=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
//        bloodGroup.setAdapter(adapter);
//
//        bloodGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String s=  parent.getItemAtPosition(position).toString();
//                Log.i("Clickeed",s);
//
//            }
//        });
//
//    }


//    private void setLocation()
//    {
//        List<String> districtList=new ArrayList<>();
//        districtList=MainActivity.districtListModel.getDistrictList().getValue();
//        ArrayAdapter<String> adapter=new ArrayAdapter<>(Activity_SearchBlood.this,R.layout.layout_drop_down_menu_single_item,districtList);
//        AutoCompleteTextView d=
//                findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
//        d.setAdapter(adapter);
//
//        d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String s=  parent.getItemAtPosition(position).toString();
//                Log.i("Clickeed",s);
//                setSubDistrict(s);
//            }
//        });

    // }
    private void setSubDistrict(String s) {
        List<String> subDistrictList = new ArrayList<>();
        subDistrictList = MainActivity.districtListModel.getDistrictListHashMap().getValue().get(s);
        Log.i("SubDistrict", String.valueOf(subDistrictList));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_SearchBlood.this, R.layout.layout_drop_down_menu_single_item, subDistrictList);
        AutoCompleteTextView d =
                findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_SubDistrict);
        d.setAdapter(adapter);

        d.setOnItemClickListener((parent, view, position, id) -> {
            String s1 = parent.getItemAtPosition(position).toString();
            Log.i("Clickeed", s1);

        });

    }

    private void checkBloodGroupField() {
        //since the text has changed so the bloodGroupField is not empty
        //so we removing the error message.
        bloodGroupTV.addTextChangedListener(new TextWatcher() {
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


}