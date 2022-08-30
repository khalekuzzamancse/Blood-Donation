package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Activity_ReadUserProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_user_profile);
        showProfile();

    }
    private void showProfile()
    {
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

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


                                TextView t=findViewById(R.id.textView4);
                                String data="Name: "+name+"\n"
                                            +"User Name: "+UserName+"\n"
                                            +"Email: "+email+"\n"
                                              + "Phone Numeber: "+phone+"\n";
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