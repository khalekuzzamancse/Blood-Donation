package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.blooddonation.database.Callback;
import com.example.blooddonation.database.BloodInfo;
import com.example.blooddonation.ui.adapters.AllUserInfoListActivity_Adapter;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class AllUserInfoList_Activity extends AppCompatActivity {
    public static final String EXTRA_bloodGroup = "BloodGroup";
    public static final String EXTRA_District = "District";
    public static final String EXTRA_SubDistrict = "SubDistrict";
    public static final String Extra_ComingFrom = "NotMain";

    RecyclerView r;
    AllUserInfoListActivity_Adapter adapter;
    CircularProgressIndicator progressIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_info_list);

        //
        progressIndicator=findViewById(R.id.progrssbar);
///
        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User List");


        Intent intent = getIntent();
        String Blood = intent.getStringExtra(EXTRA_bloodGroup);
        String Dis = intent.getStringExtra(EXTRA_District);
        String SubDis = intent.getStringExtra(EXTRA_SubDistrict);
        String comingFrom = intent.getStringExtra(Extra_ComingFrom);
        TextView tv = findViewById(R.id.textView4);
        if (!Blood.equals("null"))
            tv.setText("Search Result For: " + Blood);
        else
            tv.setText("All donar list");

        ////

        Callback callback = List -> {
            progressIndicator.setVisibility(View.INVISIBLE);
          //  Log.i("ReceivedData-AllUserInfo", String.valueOf(List));
            if (List.isEmpty())
                showSnackbar();
            updateAdapter(List);


        };
        BloodInfo db = new BloodInfo();
        if (comingFrom.equals("Main"))
        {
            progressIndicator.setVisibility(View.VISIBLE);
            db.getAllDonorList(callback);
        }


        //Note: the order of the ,if-else ladder is important
        if (!SubDis.equals("null")&&!Blood.equals("null") &&!Dis.equals("null"))
        {
            db.getBloodGroupSubDisWiseList(Blood,Dis,SubDis,callback);
            progressIndicator.setVisibility(View.VISIBLE);

        }

       else if ((!Blood.equals("null"))&&(Dis.equals("null"))&&(SubDis.equals("null"))) {
            db.getBloodGroupWiseList(Blood, callback);
            progressIndicator.setVisibility(View.VISIBLE);
        }
//        else  if (!Dis.equals("null")&&Blood.equals("null") && SubDis.equals("null"))
//            db.getDistrictWiseDonorList(Dis,callback);
        else if(!Dis.equals("null")&&!Blood.equals("null") && SubDis.equals("null")) {
            db.getBloodGroupDisWiseList(Blood, Dis, callback);
            progressIndicator.setVisibility(View.VISIBLE);
        }
//        else if (!SubDis.equals("null")&&Blood.equals("null"))
//            db.getSubDistrictWiseDonorList(Dis,SubDis,callback);


    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_for_non_home_activity_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    void updateAdapter(List<DomainUserInfo> List) {
        adapter = new AllUserInfoListActivity_Adapter(AllUserInfoList_Activity.this, List);
        r = findViewById(R.id.RecyclerView_ActivityAllUserList);
        r.setLayoutManager(new LinearLayoutManager(AllUserInfoList_Activity.this));
        r.setAdapter(adapter);
    }
    void showSnackbar() {
        Snackbar snackbar = Snackbar
                .make(progressIndicator, "Not Found", Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }


}


