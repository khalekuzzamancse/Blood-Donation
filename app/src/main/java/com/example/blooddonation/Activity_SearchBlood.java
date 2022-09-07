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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.blooddonation.ui.viewmodel.ViewModel_SearchingBlood;
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

        Button search=findViewById(R.id.Activity_SearchBlood_Button_Submit);
        search.setOnClickListener(view -> {
         p=findViewById(R.id.ActivitySearch_ProgressBar);
          //  p.setVisibility(View.VISIBLE);

            EditText bloodGroup=findViewById(R.id.Activity_SearchBlood_TextInputLayout_AutoCompleteTextView_BloodGroup);
          String blood=bloodGroup.getText().toString().trim();
            if(blood.equals(""))
            { blood="null";
            }

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
            startActivity(intent);




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



}