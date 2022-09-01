package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Activity_ReadUserProfile extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_user_profile);

        Toolbar toolbar = findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        auth=FirebaseAuth.getInstance();
       currentUser=auth.getCurrentUser();
        if(currentUser!=null) {
            showProfile();
        }


        Button update=findViewById(R.id.button);
        update.setOnClickListener(view -> {
            Intent in=new Intent(this,Activity_UpdateProfile.class);
          startActivity(in);

        });
        Button  button=findViewById(R.id.delete);

       button.setOnClickListener(view -> {
            Intent in=new Intent(this,Activity_Account_Delete.class);
            startActivity(in);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_for_non_home_activity_toolbar, menu);
        return true;



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.NonHomeActivity_MenuItem_SearchBlood) {
            Intent intent = new Intent(this, Activity_SearchBlood.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProfile() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Log.i("Curr are in Showprofie ", user.getEmail());
        Log.i("UserEmail", user.getEmail());
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
                                String name = (String) document.get("Name");
                                String email = (String) document.get("Email");
                                String phone = (String) document.get("PhoneNumber");
                                String dis = (String) document.get("District");
                                String subDis = (String) document.get("SubDistrict");
                                String age = (String) document.get("Age");
                                String gender = (String) document.get("Gender");
                                String bloodGroup = (String) document.get("BloodGroup");
                                String password=(String) document.get("Password");
                                Activity_Account_Delete.get_PassWord=password;
                                Log.i("Password set for delete", Activity_Account_Delete.get_PassWord);
                                TextView tName=findViewById(R.id.Activity_showProfile_Name);
                                if(tName!=null)
                                    tName.setText(name);
                                TextView tEmail=findViewById(R.id.Activity_showProfile_Email);
                                if(tEmail!=null)
                                    tEmail.setText(email);
                                TextView tPhone=findViewById(R.id.Activity_showProfile_Phone);
                                if(tPhone!=null)
                                    tPhone.setText(phone);
                                TextView tBlood=findViewById(R.id.Activity_showProfile_BloodGroup);
                                if(tBlood!=null&&bloodGroup!=null)
                                    tBlood.setText(bloodGroup);
                                TextView tGender=findViewById(R.id.Activity_showProfile_Gender);
                                if(tGender!=null&gender!=null)
                                    tGender.setText(gender);
                                TextView tDistrict=findViewById(R.id.Activity_showProfile_District);
                                if(tDistrict!=null&&dis!=null)
                                    tDistrict.setText(dis);
                                TextView tSubDis=findViewById(R.id.Activity_showProfile_subDistrict);
                                if(tSubDis!=null&&subDis!=null)
                                    tSubDis.setText(subDis);
                                TextView tAge=findViewById(R.id.Activity_showProfile_Age);
                                if(tAge!=null&&age!=null)
                                    tAge.setText(age);




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
