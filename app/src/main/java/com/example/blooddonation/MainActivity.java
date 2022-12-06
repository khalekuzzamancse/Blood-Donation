package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.blooddonation.database.CallbackNoOfDoc;
import com.example.blooddonation.database.CallbackStringList;
import com.example.blooddonation.database.CallbackUserProfile;
import com.example.blooddonation.database.FirebaseAuthCustom;
import com.example.blooddonation.database.BloodInfo;
import com.example.blooddonation.database.FormFillUpInfo;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.example.blooddonation.ui.viewmodel.ViewModel_AllDistrictList;
import com.example.blooddonation.ui.viewmodel.ViewModel_UserProfileInfo;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ViewModel_UserProfileInfo model;
    public static ViewModel_AllDistrictList districtListModel;
    public static String Extra_Login = "null";
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView helpline, tot_userTV, tot_donorTv;
    private String tot_Donor = "", tot_User = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.MainActivity_DrawerLayout);
        navigationView = findViewById(R.id.ActivityMain_NavDrawer_NavigationView);
        helpline = findViewById(R.id.helpline);
        tot_donorTv = findViewById(R.id.tot_donor);
        tot_userTV = findViewById(R.id.tot_user);
        BloodInfo db = new BloodInfo();
        //  progressIndicator.setVisibility(View.VISIBLE);




        ///call back for district list
        CallbackStringList callbackDistrictList=new CallbackStringList() {
            @Override
            public void receivedList(List<String> list) {
                Log.i("LISTTTT", String.valueOf(list));

            }
        };
        //getting the district list
        FormFillUpInfo locationInfo=new FormFillUpInfo();
       locationInfo.getDistricts(callbackDistrictList);
        //callback for sub district list
        CallbackStringList callbackSubDistrictList=new CallbackStringList() {
            @Override
            public void receivedList(List<String> list) {
                Log.i("LISTTTT", String.valueOf(list));

            }
        };
        //
        locationInfo.getSubDistricts("Dhaka",callbackSubDistrictList);
        ///



        //
//
        CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
            @Override
            public void getProfile(DomainUserInfo profile) {

            }
        };
        FirebaseAuthCustom authCustom = new FirebaseAuthCustom();
        authCustom.getUserInfo(callbackUserProfile);

        //callback for donor
        CallbackNoOfDoc callbackTotDonor = new CallbackNoOfDoc() {
            @Override
            public void receivedSize(int size) {
                tot_Donor = String.valueOf(size);
                tot_donorTv.setText("Total Donor : " + tot_Donor);
            }
        };
        db.getTotalDonor(callbackTotDonor);

        // <----------------->
        //

        CallbackNoOfDoc callbackNoOfDoc = new CallbackNoOfDoc() {
            @Override
            public void receivedSize(int size) {
                tot_User = String.valueOf(size);
                tot_userTV.setText("Total User : " + tot_User);
            }
        };
        db.getTotalUser(callbackNoOfDoc);

        helpline.setOnClickListener(view -> {

            startActivity(new Intent(this, HelpLineActivity.class
            ));
        });

        toolbar = findViewById(R.id.ActivityMain_ToolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ///Livedata section
        model = new ViewModelProvider(this).get(ViewModel_UserProfileInfo.class);

        model.getSignUserInfo().observe(MainActivity.this, new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> userInfo) {

                String email = userInfo.get("Email");
                if (email.equals("null")) {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_signed_in);

                }
                String isDonor = userInfo.get("isDonor");
                if (isDonor.equals("true")) {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_donor);
                } else {
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_donar);
                }


            }
        });


        districtListModel = new ViewModelProvider(this).get(ViewModel_AllDistrictList.class);
        districtListModel.getDistrictListHashMap().observe(this, new Observer<HashMap<String, List<String>>>() {
            @Override
            public void onChanged(HashMap<String, List<String>> stringListHashMap) {

                stringListHashMap.remove("");
                Log.i("Getting", String.valueOf(stringListHashMap));
            }
        });
        districtListModel.getDistrictList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Log.i("Getting", String.valueOf(strings));

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.ActivityMain_NavDrawerMenu_Login) {
                    Intent intent = new Intent(MainActivity.this, Activity_Login.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_BecomeADonor) {
                    Intent intent = new Intent(MainActivity.this, Activity_Become_Donar.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_AboutUs) {
                    Intent intent = new Intent(MainActivity.this, Activity_AboutUs.class);
                    startActivity(intent);


                } else if (id == R.id.ActivityMain_NavDrawerMenu_SearchBlood) {
                    Intent intent = new Intent(MainActivity.this, Activity_SearchBlood.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_WhyDonateBlood) {
                    Intent intent = new Intent(MainActivity.this, Activity_WhyDonateBlood.class);
                    startActivity(intent);
                } else if (id == R.id.ActivityMain_NavDrawerMenu_ContactUs) {
                    Intent intent = new Intent(MainActivity.this, Activity_ContactSendMessage.class);
                    startActivity(intent);

                } else if (id == R.id.ActivityMain_NavDrawerMenu_LogOut) {
                    FirebaseAuth.getInstance().signOut();
                    Log.i("Curr", "signout");
//                    TextView t=findViewById(R.id.headerTextView);
//                    t.setText("something");
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                } else if (id == R.id.ActivityMain_NavDrawerMenu_showProfile) {
                    Intent intent = new Intent(MainActivity.this, Activity_ReadUserProfile.class);
                    startActivity(intent);
                }
                return true;
            }
        });
//
        TextView allDonor = findViewById(R.id.All_DonorList);
        allDonor.setOnClickListener(view -> {
            Intent intent = new Intent(this, Activity_AllUserInfoList.class);
            intent.putExtra(Activity_AllUserInfoList.Extra_ComingFrom, "Main");
            intent.putExtra(Activity_AllUserInfoList.EXTRA_bloodGroup, "null");
            intent.putExtra(Activity_AllUserInfoList.EXTRA_District, "null");
            intent.putExtra(Activity_AllUserInfoList.EXTRA_SubDistrict, "null");

            startActivity(intent);
        });
        TextView t = findViewById(R.id.SearchBloodMain_Activity);
        t.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Activity_SearchBlood.class);
            startActivity(intent);

        });


    }

    @Override
    protected void onResume() {
        HashMap<String, String> data = model.getSignUserInfo().getValue();
        String email = data.get("Email");
        if (email.equals("null")) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_signed_in);

        }
        Intent i = getIntent();
        String s = i.getStringExtra(MainActivity.Extra_Login);
        if (s != null && s.equals("FromLogin")) {
            Snackbar.make(toolbar, "Login Successful", Snackbar.LENGTH_SHORT).show();
        }
        super.onResume();


    }


}



