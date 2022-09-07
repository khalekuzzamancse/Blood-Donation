package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
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
import android.widget.TextView;

import com.example.blooddonation.ui.viewmodel.ViewModel_SearchingBlood;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
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
    TextInputLayout bloodGroupLayout;

    List<String> subDistrictList=new ArrayList<>();
    ProgressBar p;
    EditText bloodGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);
        Toolbar toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Blood");
        setBloodGroup();
        setLocation();

        bloodGroup=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
   bloodGroupLayout=findViewById(R.id.Activity_SearchBlood_TextInputLayout_BloodGroup);

        Button search=findViewById(R.id.Activity_SearchBlood_Button_Submit);
        //check if the blood group filed is empty or not
        //if the there are any error  message then the checkedBloodGroupField() will clear or do not clear the message
       checkBloodGroupField();
        search.setOnClickListener(view -> {
            bloodGroupLayout.setError(null);
         p=findViewById(R.id.ActivitySearch_ProgressBar);
          //  p.setVisibility(View.VISIBLE);

            String blood=bloodGroup.getText().toString().trim();



            EditText Dis=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_District);
            String dis=Dis.getText().toString().trim();
            if(dis.equals(""))
                dis="null";
            EditText SubDis=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_SubDistrict);
            String subDis=SubDis.getText().toString().trim();

            if(subDis.equals(""))
                subDis="null";

            Intent intent=new Intent(this,Activity_AllUserInfoList.class);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_bloodGroup,blood);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_District,dis);
            intent.putExtra(Activity_AllUserInfoList.EXTRA_SubDistrict,subDis);
         //  p.setVisibility(View.INVISIBLE);
            //if the user not choosen a bloodGroup then we do not show the list

            if(!bloodGroup.getText().toString().isEmpty())
           startActivity(intent);
            else
            {
                //if user not chosen a bloodGroup but clicked the submit button.
                //then show the error message
                bloodGroupLayout.setError("Please,Choose a blood Group");
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
        int id=item.getItemId();

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


    private void setLocation()
    {
        List<String> districtList=new ArrayList<>();
        districtList=MainActivity.districtListModel.getDistrictList().getValue();
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
    private void setSubDistrict(String s)
    {
        List<String>subDistrictList=new ArrayList<>();
        subDistrictList=MainActivity.districtListModel.getDistrictListHashMap().getValue().get(s);
        Log.i("SubDistrict", String.valueOf(subDistrictList));

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

    }
private void checkBloodGroupField()
    {
        //since the text has changed so the bloodGroupField is not empty
        //so we removing the error message.
        bloodGroup.addTextChangedListener(new TextWatcher() {
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