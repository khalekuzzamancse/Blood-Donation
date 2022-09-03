package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    public static List<Integer>Li=new ArrayList<>();

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("List size","oncreate");

         drawerLayout=findViewById(R.id.MainActivity_DrawerLayout);
        navigationView=findViewById(R.id.ActivityMain_NavDrawer_NavigationView);


        Toolbar toolbar=findViewById(R.id.ActivityMain_ToolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle
                (this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //setting the menu based on condition
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();
        if(currentUser==null)
        {

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_signed_in);

        }
        else
        {


            Log.i("List size,next,execute showProfile", String.valueOf(SingedInUserInfo.x));
            //showProfile();

          FirebaseFirestore  db = FirebaseFirestore.getInstance();
            db.collection("UserInfo")
                    .document(currentUser.getEmail())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot q, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                //
                            } else {
                                Log.i("Alahmdulliah","Get");

                                DocumentSnapshot document =q;
                                    String name= (String) document.get("Name");
                                    String email= (String) document.get("Email");
                                    String phone= (String) document.get("PhoneNumber");
                                    String UserName= (String) document.get("UserName");
                                    String dis= (String) document.get("District");
                                    String subDis= (String) document.get("SubDistrict");
                                    String isDonor=(String) document.get("isDonor");
                                    if(isDonor.equals("true"))
                                    {
                                        navigationView.getMenu().clear();
                                        navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_donor);
                                    }
                                    else
                                    {
                                        navigationView.getMenu().clear();
                                        navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_donar);
                                    }

                                    TextView navbarName=findViewById(R.id.NavbarHeader_Name);
                                    if(navbarName!=null)
                                        navbarName.setText(name);
                                    TextView navbarLocation=findViewById(R.id.NavbarHeader_Location);
                                    if (navbarLocation!=null)
                                        navbarLocation.setText(dis+","+subDis);
                                    SingedInUserInfo.x++;
                                    Log.i("List size,at,execute showProfile", String.valueOf(SingedInUserInfo.x));


                                }
                        }
                    });

            Log.i("List size,after showProfile", String.valueOf(SingedInUserInfo.x));
        }



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.ActivityMain_NavDrawerMenu_Login)
                {
                    Intent intent=new Intent(MainActivity.this,Activity_Login.class);
                    startActivity(intent);
                }
              else if(id==R.id.ActivityMain_NavDrawerMenu_BecomeADonor)
                {
                    Intent intent=new Intent(MainActivity.this,Activity_Become_Donar.class);
                    startActivity(intent);
                }
                else if(id==R.id.ActivityMain_NavDrawerMenu_AboutUs)
                {
                    Intent intent=new Intent(MainActivity.this,Activity_AboutUs.class);
                    startActivity(intent);


                }
                else if(id==R.id.ActivityMain_NavDrawerMenu_SearchBlood)
                {
                    Intent intent=new Intent(MainActivity.this,Activity_SearchBlood.class);
                    startActivity(intent);
                }
                else if(id==R.id.ActivityMain_NavDrawerMenu_WhyDonateBlood)
                {
                    Intent intent=new Intent(MainActivity.this,Activity_WhyDonateBlood.class);
                    startActivity(intent);
                }
                else if(id==R.id.ActivityMain_NavDrawerMenu_ContactUs)
                {
                    Intent intent=new Intent(MainActivity.this,Activity_ContactSendMessage.class);
                    startActivity(intent);

                }
                else if(id==R.id.ActivityMain_NavDrawerMenu_LogOut)
                {
              FirebaseAuth.getInstance().signOut();
//                    TextView t=findViewById(R.id.headerTextView);
//                    t.setText("something");
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
                else if(id==R.id.ActivityMain_NavDrawerMenu_showProfile)
                {
                    Intent intent=new Intent(MainActivity.this,Activity_ReadUserProfile.class);
                    startActivity(intent);
                }
                return true;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
      //  Log.i("List","Resume");


    }
    @Override
    protected void onStart() {
        super.onStart();
      //  Log.i("List", "Start");
    }
    @Override
    protected void onPause() {
        super.onPause();
       // Log.i("List", "Pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("List", "Destroy");
    }

    private void showProfile()
    {
        FirebaseAuth mAuth; mAuth=FirebaseAuth.getInstance();
      FirebaseUser  user = mAuth.getCurrentUser();
//    Log.i("Curr are in Showprofie ",user.getEmail());
//        Log.i("UserEmail",user.getEmail());
        Log.i("List size,Entering,execute showProfile", String.valueOf(SingedInUserInfo.x));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("UserInfo")
                .document(user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name= (String) document.get("Name");
                                String email= (String) document.get("Email");
                                String phone= (String) document.get("PhoneNumber");
                                String UserName= (String) document.get("UserName");
                                String dis= (String) document.get("District");
                                String subDis= (String) document.get("SubDistrict");
                               String isDonor=(String) document.get("isDonor");
                                if(isDonor.equals("true"))
                                {
                                    navigationView.getMenu().clear();
                                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_donor);
                                }
                                else
                                {
                                    navigationView.getMenu().clear();
                                    navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_donar);
                                }

                                TextView navbarName=findViewById(R.id.NavbarHeader_Name);
                                if(navbarName!=null)
                                navbarName.setText(name);
                                TextView navbarLocation=findViewById(R.id.NavbarHeader_Location);
                                if (navbarLocation!=null)
                                    navbarLocation.setText(dis+","+subDis);
                               SingedInUserInfo.x++;
                                Log.i("List size,at,execute showProfile", String.valueOf(SingedInUserInfo.x));



                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }


}