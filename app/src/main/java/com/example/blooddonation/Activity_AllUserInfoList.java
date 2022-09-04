package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.blooddonation.ui.viewholders.AllUserInfoListActivity_ViewHolder;
import com.example.blooddonation.ui.viewmodel.ViewModel_SearchingBlood;

import java.util.HashMap;

public class Activity_AllUserInfoList extends AppCompatActivity {
    HashMap<String, HashMap<String, String>> AllUserList = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user_info_list);
        AllUserList= ViewModel_SearchingBlood.UserInfoListByEmail;
        Log.i("ALLUser", String.valueOf(AllUserList));


    }
}