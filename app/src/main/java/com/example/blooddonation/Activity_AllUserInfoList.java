package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private List<AllUserInfoListActivity_DataType> list;
    private List<AllUserInfoListActivity_DataType> DistrictWiseList;
    private List<AllUserInfoListActivity_DataType> SubDistrictWiseList;
    private List<AllUserInfoListActivity_DataType> BloodGroupWiseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_info_list);


        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All User List");
        model = new ViewModelProvider(this).get(ViewModel_SearchingBlood.class);
        model.getUserInfoListByEmail().observe(this, new Observer<HashMap<String, HashMap<String, String>>>() {
            @Override
            public void onChanged(HashMap<String, HashMap<String, String>> stringHashMapHashMap) {
                Log.i("Alhamdulliah,ALl", String.valueOf(stringHashMapHashMap));

            }
        });
        model.getUserInfoListByBloodGroup().observe(this, new Observer<HashMap<String, List<String>>>() {
            @Override
            public void onChanged(HashMap<String, List<String>> stringListHashMap) {
                Log.i("Alhamdulliah,BloodGroup", String.valueOf(stringListHashMap));
            }
        });


//
////hello
//        list = new ArrayList<>();
//        DistrictWiseList = new ArrayList<>();
//        SubDistrictWiseList = new ArrayList<>();
//        BloodGroupWiseList = new ArrayList<>();



//       //  getUserList();
////        getDistrictWiseList("Dhaka");
////        getSubDistrictWiseList("Uttara");
//        AllUserInfoListActivity_Adapter adapter =  adapter=new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this, BloodGroupWiseList);
//        RecyclerView r = findViewById(R.id.RecyclerView_ActivityAllUserList);
//        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
//        r.setAdapter(adapter);


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

//    private void getUserList() {
//
//        for (String key1 : obj.UserInfoListByEmail.keySet()) {
//            AllUserInfoListActivity_DataType data = new AllUserInfoListActivity_DataType();
//            HashMap<String, String> innerMap = obj.UserInfoListByEmail.get(key1);
//            //   Log.i("OKAy",innerMap.get("Name")+"_>"+innerMap.get("Email"));
//            AddData(innerMap, "All");
//        }
//
//
//    }
//
//    private void getDistrictWiseList(String s) {
//        HashMap<String, List<String>> ByDistrictHashMap = new HashMap<>();
//        ByDistrictHashMap = obj.UserInfoListByDistrict;
//        List<String> Li = ByDistrictHashMap.get(s);
//        if (Li != null)
//            for (int i = 0; i < Li.size(); i++) {
//                {
//                    String UserEmail = Li.get(i);
//                    HashMap<String, String> innerMap = obj.UserInfoListByEmail.get(UserEmail);
//                    AddData(innerMap, "Dis");
//
//                }
//
//
//            }
//        else
//            return;
//        Li.clear();
//    }
//
//    private void getSubDistrictWiseList(String s) {
//        HashMap<String, List<String>> BySubDistrictHashMap = new HashMap<>();
//        BySubDistrictHashMap = obj.UserInfoListBySubDistrict;
//        List<String> Li = BySubDistrictHashMap.get(s);
//        if (Li != null)
//            for (int i = 0; i < Li.size(); i++) {
//                {
//                    String UserEmail = Li.get(i);
//                    HashMap<String, String> innerMap = obj.UserInfoListByEmail.get(UserEmail);
//                    AddData(innerMap, "Sub");
//                    //  Log.i("OKAy", String.valueOf(innerMap));
//
//                }
//
//
//            }
//        else
//            return;
//        Li.clear();
//    }
//
//    private void getBloodGroupWiseList(String s) {
//        HashMap<String, List<String>> ByBloodHashMap = new HashMap<>();
//        ByBloodHashMap = obj.UserInfoListByBloodGroup;
//        List<String> Li =new ArrayList<>();
//                if(ByBloodHashMap.get(s)==null)
//                {
//                    Li.clear();
//                    return;
//                }
//                else
//                    Li=ByBloodHashMap.get(s);
//     Log.i("REsume,LI,Direct", String.valueOf(ByBloodHashMap.get(s)));
//            for (int i = 0; i < Li.size(); i++) {
//                {
//                    String UserEmail = Li.get(i);
//                    HashMap<String, String> innerMap = obj.UserInfoListByEmail.get(UserEmail);
//                    AddData(innerMap, "Blood");
//                    //  Log.i("OKAy", String.valueOf(innerMap));
//
//                }
//
//
//            }
//            Li.clear();
//
//    }
//
//
//    private void AddData(HashMap<String, String> innerMap, String listName) {
//        AllUserInfoListActivity_DataType data = new AllUserInfoListActivity_DataType();
//        //Log.i("HEYYY",UserEmail+"->"+Li.size());
//        String name = innerMap.get("Name");
//        if (name != null)
//            data.Name = name;
//        else
//            data.Name = "Unknown";
//        String email = innerMap.get("Email");
//        if (email != null)
//            data.Email = email;
//        else
//            data.Email = "Unknown";
//        String phone = innerMap.get("PhoneNumber");
//        if (phone != null)
//            data.PhoneNumber = phone;
//        else
//            data.PhoneNumber = "Unknown";
//        String gender = innerMap.get("Gender");
//        if (gender != null)
//            data.Gender = gender;
//        else
//            data.Gender = "Unknown";
//        String bloodGroup = innerMap.get("BloodGroup");
//        if (bloodGroup != null)
//            data.BloodGroup = bloodGroup;
//        else
//            data.BloodGroup = "Unknown";
//        String dis = innerMap.get("District");
//        if (dis != null)
//            data.District = dis;
//        else
//            data.District = "Unknown";
//        String subDis = innerMap.get("SubDistrict");
//        if (subDis != null)
//            data.SubDistrict = subDis;
//        else
//            data.SubDistrict = "Unknown";
//        ///
//        HashMap<String, String> tmp = MainActivity.model.getSignUserInfo().getValue();
//        String CurrentUserEmail = tmp.get("Email");
//        if (CurrentUserEmail.equals("null")) {
//            data.Email = "Available";
//            data.PhoneNumber = "Available";
//        }
//        //Log.i("OKAY",data.Name+"->"+data.Email+"->"+data.PhoneNumber+"->"+data.Gender+"->"+data.BloodGroup+"->"+data.District+"->"+data.SubDistrict);
//        if (listName.equals("All"))
//            list.add(data);
//        else if (listName.equals("Dis"))
//            DistrictWiseList.add(data);
//        else if (listName.equals("Sub"))
//            SubDistrictWiseList.add(data);
//        else if (listName.equals("Blood"))
//            BloodGroupWiseList.add(data);
//    }

    @Override
    protected void onResume() {
        Log.i("REsume","OK");
        super.onResume();
    }
}


