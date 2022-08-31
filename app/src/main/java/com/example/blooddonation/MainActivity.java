package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout=findViewById(R.id.MainActivity_DrawerLayout);
        NavigationView navigationView=findViewById(R.id.ActivityMain_NavDrawer_NavigationView);

        //setting the menu based on condition
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=auth.getCurrentUser();

        if(currentUser==null)
        {
            Log.i("Current User,at Main","Nulled");
            //if the user not singed in
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_signed_in);
            CurrentUserInfo.name="Null";

        }
        else
        {
            Log.i("Current User,at Main",currentUser.getEmail());
            showProfile();
            //if the user singed in
            if(CurrentUserInfo.isDonor.equals("true"))
            {
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_donor);
            }
            else if(CurrentUserInfo.isDonor.equals("false"))
            {
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.menu_nav_drawer_when_user_not_donar);
            }



        }


        Toolbar toolbar=findViewById(R.id.ActivityMain_ToolBar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle
                (this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null)
        {
            showProfile();

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
                    TextView t=findViewById(R.id.headerTextView);
                    t.setText("something");
                    CurrentUserInfo.name="Null";
                   //restarting the main activity so the navbar and menu get updated.
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                }
                return true;
            }
        });


    }

    private void showProfile()
    {
        FirebaseAuth mAuth; mAuth=FirebaseAuth.getInstance();
      FirebaseUser  user = mAuth.getCurrentUser();

        Log.i("UserEmail",user.getEmail());
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
                                String isDonor= (String) document.get("isDonor");
                                CurrentUserInfo.name=name;
                              //  if(isDonor!=null)
                                CurrentUserInfo.isDonor=isDonor;

                                TextView t=findViewById(R.id.headerTextView);
                                String data= "User Name: "+UserName+"\n"+"Email: "+email+"\n";
                                t.setText(data);
                                Log.i("Alhamdulliah",data);


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