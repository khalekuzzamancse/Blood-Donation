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

                }
                return true;
            }
        });
//        Read();
//        Write();


    }

    private void Read()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("a")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Hello", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void Write()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
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