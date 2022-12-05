package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.blooddonation.database.Callback;
import com.example.blooddonation.database.FirebaseCustom;
import com.example.blooddonation.ui.adapters.AllUserInfoListActivity_Adapter;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;

import java.util.ArrayList;
import java.util.List;

public class Activity_AllUserInfoList extends AppCompatActivity {
    public static final String EXTRA_bloodGroup = "BloodGroup";
    public static final String EXTRA_District = "District";
    public static final String EXTRA_SubDistrict = "SubDistrict";
    public static final String Extra_ComingFrom = "NotMain";
    private List<DomainUserInfo> list;
    RecyclerView r;
    AllUserInfoListActivity_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_info_list);

///
        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        list = new ArrayList<>();

        Callback callback = new Callback() {
            @Override
            public void receivedList(List<DomainUserInfo> List) {
                list = List;
                Log.i("ReceivedData-AllUserInfo", String.valueOf(List));
                updateAdapter(List);
            }
        };
        FirebaseCustom db = new FirebaseCustom();
        if (comingFrom.equals("Main"))
            db.getAllDonorList(callback);

        //Note: the order of the ,if-else ladder is important
        if (!SubDis.equals("null")&&!Blood.equals("null") &&!Dis.equals("null"))
            db.getBloodGroupSubDisWiseList(Blood,Dis,SubDis,callback);
       else if ((!Blood.equals("null"))&&(Dis.equals("null"))&&(SubDis.equals("null")))
                db.getBloodGroupWiseList(Blood,callback);
//        else  if (!Dis.equals("null")&&Blood.equals("null") && SubDis.equals("null"))
//            db.getDistrictWiseDonorList(Dis,callback);
        else if(!Dis.equals("null")&&!Blood.equals("null") && SubDis.equals("null"))
            db.getBloodGroupDisWiseList(Blood,Dis,callback);
//        else if (!SubDis.equals("null")&&Blood.equals("null"))
//            db.getSubDistrictWiseDonorList(Dis,SubDis,callback);


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


    void updateAdapter(List<DomainUserInfo> List) {
        adapter = new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, List);
        r = findViewById(R.id.RecyclerView_ActivityAllUserList);
        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
        r.setAdapter(adapter);
    }

}


