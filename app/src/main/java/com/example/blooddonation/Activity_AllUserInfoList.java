package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.blooddonation.ui.adapters.AdapterForRecyclerViewLoggedIn;
import com.example.blooddonation.ui.adapters.AllUserInfoListActivity_Adapter;
import com.example.blooddonation.ui.datatypes.AllUserInfoListActivity_DataType;
import com.example.blooddonation.ui.viewmodel.ViewModel_SearchingBlood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_AllUserInfoList extends AppCompatActivity {
   private List<AllUserInfoListActivity_DataType>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_info_list);

        Toolbar toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All User List");
//hello
        list=new ArrayList<>();
        getUserList();

        AllUserInfoListActivity_Adapter adapter=new AllUserInfoListActivity_Adapter(Activity_AllUserInfoList.this,list);
        RecyclerView r=findViewById(R.id.RecyclerView_ActivityAllUserList);
        r.setLayoutManager(new LinearLayoutManager(Activity_AllUserInfoList.this));
        r.setAdapter(adapter);



//
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
    private void getUserList()
    {

        for (String key1 : ViewModel_SearchingBlood.UserInfoListByEmail.keySet()) {
            AllUserInfoListActivity_DataType data=new AllUserInfoListActivity_DataType();
            HashMap<String,String> innerMap = ViewModel_SearchingBlood.UserInfoListByEmail.get(key1);
            //   Log.i("OKAy",innerMap.get("Name")+"_>"+innerMap.get("Email"));
            String name=innerMap.get("Name");
            if(name!=null)
                data.Name=name;
            else
                data.Name="Unknown";
            String email=innerMap.get("Email");
            if(email!=null)
                data.Email=email;
            else
                data.Email="Unknown";
            String phone=innerMap.get("PhoneNumber");
            if(phone!=null)
                data.PhoneNumber=phone;
            else
                data.PhoneNumber="Unknown";
            String gender=innerMap.get("Gender");
            if(gender!=null)
                data.Gender=gender;
            else
                data.Gender="Unknown";
            String bloodGroup=innerMap.get("BloodGroup");
            if(bloodGroup!=null)
                data.BloodGroup=bloodGroup;
            else
                data.BloodGroup="Unknown";
            String dis=innerMap.get("District");
            if(dis!=null)
                data.District=dis;
            else
                data.District="Unknown";
            String subDis=innerMap.get("SubDistrict");
            if(subDis!=null)
                data.SubDistrict=subDis;
            else
                data.SubDistrict="Unknown";
            ///
            HashMap<String, String>tmp= MainActivity.model.getSignUserInfo().getValue();
            String CurrentUserEmail = tmp.get("Email");
            if (CurrentUserEmail.equals("null")) {
                data.Email="Available";
                data.PhoneNumber="Available";
            }
            //Log.i("OKAY",data.Name+"->"+data.Email+"->"+data.PhoneNumber+"->"+data.Gender+"->"+data.BloodGroup+"->"+data.District+"->"+data.SubDistrict);
            list.add(data);
        }


    }

}