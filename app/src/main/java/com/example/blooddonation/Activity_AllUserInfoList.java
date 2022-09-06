package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.blooddonation.ui.adapters.AllUserInfoListActivity_Adapter;
import com.example.blooddonation.ui.datatypes.AllUserInfoListActivity_DataType;
import com.example.blooddonation.ui.viewmodel.ViewModel_SearchingBlood;
import com.example.blooddonation.ui.viewmodel.ViewModel_UserProfileInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_AllUserInfoList extends AppCompatActivity {
    public static final String EXTRA_bloodGroup = "BloodGroup";
    public static final String EXTRA_District = "District";
    public static final String EXTRA_SubDistrict = "SubDistrict";
    private ViewModel_SearchingBlood model;

    private HashMap<String, List<String>> DistrictWiseHashMap;
    private HashMap<String, List<String>> subDistrictWiseHashMap;
    private HashMap<String, List<String>> BloodGroupWiseHashMap;
    private HashMap<String, HashMap<String, String>> AllUserHashMap;


    private List<AllUserInfoListActivity_DataType> list;
    private List<AllUserInfoListActivity_DataType> DistrictWiseList;
    private List<AllUserInfoListActivity_DataType> SubDistrictWiseList;
    private List<AllUserInfoListActivity_DataType> BloodGroupWiseList;
    private List<AllUserInfoListActivity_DataType> BloodGroup_DistrictWiseList;
    private List<AllUserInfoListActivity_DataType> BloodGroup_SubDistrictWiseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_info_list);

///
        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All User List");


        DistrictWiseHashMap = new HashMap<>();
        AllUserHashMap = new HashMap<>();
        subDistrictWiseHashMap = new HashMap<>();
        BloodGroupWiseHashMap = new HashMap<>();


        list = new ArrayList<>();
        DistrictWiseList = new ArrayList<>();
        SubDistrictWiseList = new ArrayList<>();
        BloodGroupWiseList = new ArrayList<>();
        BloodGroup_DistrictWiseList = new ArrayList<>();
        BloodGroup_SubDistrictWiseList = new ArrayList<>();

        Intent intent = getIntent();
        String Blood = intent.getStringExtra(EXTRA_bloodGroup);
        String Dis = intent.getStringExtra(EXTRA_District);
        String SubDis = intent.getStringExtra(EXTRA_SubDistrict);
        Log.i("Getted", Blood + "->" + Dis + "->" + SubDis);


        model = new ViewModelProvider(this).get(ViewModel_SearchingBlood.class);
        model.getUserInfoListByEmail().observe(this, new Observer<HashMap<String, HashMap<String, String>>>() {
            @Override
            public void onChanged(HashMap<String, HashMap<String, String>> stringHashMapHashMap) {
                //   Log.i("Alhamdulliah,ALl", String.valueOf(stringHashMapHashMap));
                AllUserHashMap = stringHashMapHashMap;
                getUserList();
                if (Blood.equals("null") && Dis.equals("null") && SubDis.equals("null")) {
                    //Log.i("USEALL", String.valueOf(stringHashMapHashMap));
                    AllUserInfoListActivity_Adapter adapter = adapter = new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, list);
                    RecyclerView r = findViewById(R.id.RecyclerView_ActivityAllUserList);
                    r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
                    r.setAdapter(adapter);
                }


            }
        });
        //insha-allah
        if ((!Blood.equals("null"))) {
            model.getUserInfoListByBloodGroup().observe(this, new Observer<HashMap<String, List<String>>>() {
                @Override
                public void onChanged(HashMap<String, List<String>> stringListHashMap) {
                    // Log.i("Alhamdulliah,BloodGroup", String.valueOf(stringListHashMap));
                    BloodGroupWiseHashMap = stringListHashMap;
                    getBloodGroupWiseList(Blood);
                    if (Dis.equals("null") && SubDis.equals("null")) {
                        AllUserInfoListActivity_Adapter adapter = adapter = new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, BloodGroupWiseList);
                        RecyclerView r = findViewById(R.id.RecyclerView_ActivityAllUserList);
                        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
                        r.setAdapter(adapter);
                    }

                }
            });
        }


        if (!Dis.equals("null")) {
            model.getUserInfoListByDistrict().observe(this, new Observer<HashMap<String, List<String>>>() {
                @Override
                public void onChanged(HashMap<String, List<String>> stringListHashMap) {
                    //  Log.i("Alhamdulliah,District", String.valueOf(stringListHashMap));
                    DistrictWiseHashMap = stringListHashMap;
                    getDistrictWiseList(Dis);
                    if (Blood.equals("null") && SubDis.equals("null")) {
                        AllUserInfoListActivity_Adapter adapter = adapter = new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, DistrictWiseList);
                        RecyclerView r = findViewById(R.id.RecyclerView_ActivityAllUserList);
                        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
                        r.setAdapter(adapter);
                    }
                    if (!Blood.equals("null")) {
                        getBloodGroup_DistrictWiseList(Blood);
                        AllUserInfoListActivity_Adapter adapter = adapter = new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, BloodGroup_DistrictWiseList);
                        RecyclerView r = findViewById(R.id.RecyclerView_ActivityAllUserList);
                        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
                        r.setAdapter(adapter);
                    }


                }
            });
        }
        if (!SubDis.equals("null")) {
            model.getUserInfoListBySubDistrict().observe(this, new Observer<HashMap<String, List<String>>>() {
                @Override
                public void onChanged(HashMap<String, List<String>> stringListHashMap) {
                    //   Log.i("Alhamdulliah,SubDistrict", String.valueOf(stringListHashMap));
                    ;
                    subDistrictWiseHashMap = stringListHashMap;
                    getSubDistrictWiseList(SubDis);
                    if (Blood.equals("null") && !Dis.equals("null")) {
                        AllUserInfoListActivity_Adapter adapter = adapter = new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, SubDistrictWiseList);
                        RecyclerView r = findViewById(R.id.RecyclerView_ActivityAllUserList);
                        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
                        r.setAdapter(adapter);
                    }
                    else if(!Blood.equals("null") && !Dis.equals("null"))
                    {
                        getBloodGroup_SubDistrictWiseList(Blood);
                        AllUserInfoListActivity_Adapter adapter = adapter = new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, BloodGroup_SubDistrictWiseList);
                        RecyclerView r = findViewById(R.id.RecyclerView_ActivityAllUserList);
                        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
                        r.setAdapter(adapter);

                    }

                }
            });
        }


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

    private void getUserList() {

        for (String key1 : AllUserHashMap.keySet()) {
            if (!key1.equals("")) {

                AllUserInfoListActivity_DataType data = new AllUserInfoListActivity_DataType();
                HashMap<String, String> innerMap = AllUserHashMap.get(key1);
                //   Log.i("OKAy",innerMap.get("Name")+"_>"+innerMap.get("Email"));
                AddData(innerMap, "All");
            }


        }


    }

    private void getDistrictWiseList(String s) {
        List<String> ListDis = DistrictWiseHashMap.get(s);
        if (ListDis == null)
            ListDis = new ArrayList<>();

        for (int i = 0; i < ListDis.size(); i++) {
            {
                String UserEmail = ListDis.get(i);
                HashMap<String, String> innerMap = AllUserHashMap.get(UserEmail);
                AddData(innerMap, "Dis");

            }


        }
    }

    private void getSubDistrictWiseList(String s) {
        List<String> ListSubDis = subDistrictWiseHashMap.get(s);
        if (ListSubDis == null)
            ListSubDis = new ArrayList<>();
        for (int i = 0; i < ListSubDis.size(); i++) {
            {
                String UserEmail = ListSubDis.get(i);
                HashMap<String, String> innerMap = AllUserHashMap.get(UserEmail);
                AddData(innerMap, "Sub");

            }


        }
    }

    //
    private void getBloodGroupWiseList(String s) {
        List<String> ListBlood = BloodGroupWiseHashMap.get(s);
        if (ListBlood == null)
            ListBlood = new ArrayList<>();
        for (int i = 0; i < ListBlood.size(); i++) {
            {
                String UserEmail = ListBlood.get(i);
                HashMap<String, String> innerMap = AllUserHashMap.get(UserEmail);
                AddData(innerMap, "Blood");
            }


        }

    }

    //
//
    private void AddData(HashMap<String, String> innerMap, String listName) {
        AllUserInfoListActivity_DataType data = new AllUserInfoListActivity_DataType();
        //Log.i("HEYYY",UserEmail+"->"+Li.size());
        String name = innerMap.get("Name");
        if (name != null)
            data.Name = name;
        String email = innerMap.get("Email");
        if (email != null)
            data.Email = email;
        String phone = innerMap.get("PhoneNumber");
        if (phone != null)
            data.PhoneNumber = phone;
        String gender = innerMap.get("Gender");
        if (gender != null)
            data.Gender = gender;
        String bloodGroup = innerMap.get("BloodGroup");
        if (bloodGroup != null)
            data.BloodGroup = bloodGroup;
        String dis = innerMap.get("District");
        if (dis != null)
            data.District = dis;
        String subDis = innerMap.get("SubDistrict");
        if (subDis != null)
            data.SubDistrict = subDis;
        ///
        //    Log.i("OKAY",data.Name+"->"+data.Email+"->"+data.PhoneNumber+"->"+data.Gender+"->"+data.BloodGroup+"->"+data.District+"->"+data.SubDistrict);
        if (listName.equals("All"))
            list.add(data);
        else if (listName.equals("Dis"))
            DistrictWiseList.add(data);
        else if (listName.equals("Sub"))
            SubDistrictWiseList.add(data);
        else if (listName.equals("Blood"))
            BloodGroupWiseList.add(data);
    }


    private void getBloodGroup_DistrictWiseList(String blood) {
        for (int i = 0; i < DistrictWiseList.size(); i++) {
            AllUserInfoListActivity_DataType obj = new AllUserInfoListActivity_DataType();
            obj = DistrictWiseList.get(i);
            if (obj != null)
                if (obj.BloodGroup.equals(blood))
                    BloodGroup_DistrictWiseList.add(obj);
        }

    }

    private void getBloodGroup_SubDistrictWiseList(String blood) {
        for (int i = 0; i < SubDistrictWiseList.size(); i++) {
            AllUserInfoListActivity_DataType obj = new AllUserInfoListActivity_DataType();
            obj = SubDistrictWiseList.get(i);
            if (obj != null)
                if (obj.BloodGroup.equals(blood))
                    BloodGroup_SubDistrictWiseList.add(obj);
        }
    }

    @Override
    protected void onResume() {
        Log.i("REsume", "OK");
        super.onResume();
    }
}


