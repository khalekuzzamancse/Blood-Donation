package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.blooddonation.database.CallbackNoOfDoc;
import com.example.blooddonation.database.CallbackUserProfile;
import com.example.blooddonation.database.FirebaseAuthCustom;
import com.example.blooddonation.database.BloodInfo;
import com.example.blooddonation.ui.datatypes.DomainUserInfo;
import com.example.blooddonation.viewmodel.ViewModel_AllDistrictList;
import com.example.blooddonation.viewmodel.ViewModel_UserProfileInfo;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.Date;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {
    public static ViewModel_UserProfileInfo model;
    public static ViewModel_AllDistrictList districtListModel;
    public static String Extra_Login = "null";
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    TextView helpline, tot_userTV, tot_donorTv, allDonor, searchTV;
    private String tot_Donor = "", tot_User = "";
    DomainUserInfo userInfo;
    FirebaseAuthCustom currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        BloodInfo db = new BloodInfo();
        //  progressIndicator.setVisibility(View.VISIBLE);
        initialize();
        setToolbar();
        hideMenuItem();
        //we have to called hideMenu option two times
        //1:when the main activity is just stared and we check the user singed in or not
        //2:after main activity start,read the user profile(if singed in) then
        //2.1 based on signed in info hide some menu

        //getting the login user profile info,


        CallbackUserProfile callbackUserProfile = new CallbackUserProfile() {
            @Override
            public void getProfile(DomainUserInfo profile) {
                userInfo = profile;
                hideMenuItem();
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


        ///Livedata section


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.login)
                    item.setVisible(false);
                if (id == R.id.login) {
                    Intent intent = new Intent(MainActivity.this, Activity_Login.class);
                    startActivity(intent);
                } else if (id == R.id.become_a_donor) {
                    Intent intent = new Intent(MainActivity.this, Become_Donor_Activity.class);
                    startActivity(intent);
                } else if (id == R.id.about_us) {
                    Intent intent = new Intent(MainActivity.this, Activity_AboutUs.class);
                    startActivity(intent);


                } else if (id == R.id.search_blood) {
                    Intent intent = new Intent(MainActivity.this, Activity_SearchBlood.class);
                    startActivity(intent);
                } else if (id == R.id.why_donate_blood) {
                    Intent intent = new Intent(MainActivity.this, Activity_WhyDonateBlood.class);
                    startActivity(intent);
                } else if (id == R.id.contact_us) {
                    Intent intent = new Intent(MainActivity.this, Activity_ContactSendMessage.class);
                    startActivity(intent);

                } else if (id == R.id.logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                } else if (id == R.id.show_profile) {
                    Intent intent = new Intent(MainActivity.this, ShowProfile_Activity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
//

        allDonor.setOnClickListener(view -> {
            Intent intent = new Intent(this, AllUserInfoList_Activity.class);
            intent.putExtra(AllUserInfoList_Activity.Extra_ComingFrom, "Main");
            intent.putExtra(AllUserInfoList_Activity.EXTRA_bloodGroup, "null");
            intent.putExtra(AllUserInfoList_Activity.EXTRA_District, "null");
            intent.putExtra(AllUserInfoList_Activity.EXTRA_SubDistrict, "null");

            startActivity(intent);
        });

        searchTV.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Activity_SearchBlood.class);
            startActivity(intent);

        });

    }

    private void hideMenuItem() {
        Menu menu = navigationView.getMenu();
        // if the user not singed in then hide
        //become a donor,profile and logout option from the menu
        if (currentUser.getUser() == null) {
            menu.removeItem(R.id.become_a_donor);
            menu.removeItem(R.id.logout);
            menu.removeItem(R.id.show_profile);
        }
        //if the user is already login and he is donor then
        //hide the login option ->since he already login
        //hide the become donor because he is already a donor
        else if (currentUser.getUser() != null && userInfo.BloodGroup != null) {
            menu.removeItem(R.id.login);
            menu.removeItem(R.id.become_a_donor);
        }
        //if the user is already login and he is not donor then
        //hide the login option ->since he already login
        else if (currentUser.getUser() != null && userInfo.BloodGroup == null) {
            menu.removeItem(R.id.login);
        }


    }

    private void setToolbar() {
        toolbar = findViewById(R.id.ActivityMain_ToolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle
                (this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void initialize() {
        drawerLayout = findViewById(R.id.MainActivity_DrawerLayout);
        navigationView = findViewById(R.id.ActivityMain_NavDrawer_NavigationView);
        helpline = findViewById(R.id.helpline);
        tot_donorTv = findViewById(R.id.tot_donor);
        tot_userTV = findViewById(R.id.tot_user);
        allDonor = findViewById(R.id.All_DonorList);
        searchTV = findViewById(R.id.SearchBloodMain_Activity);
        userInfo = new DomainUserInfo();
        currentUser = new FirebaseAuthCustom();
    }


}



