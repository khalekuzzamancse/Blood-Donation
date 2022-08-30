package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scrolable);


        Toolbar toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");


        Button register=findViewById(R.id.ActivityLogin_Button_Register);
        register.setOnClickListener((view)->{
            Intent intent=new Intent(this,Activity_Register.class);
            startActivity(intent);
        });
        Button login=findViewById(R.id.ActivityLogin_Button_Login);
        login.setOnClickListener(view -> {
            EditText Email=findViewById(R.id.ActivityLogin_EditText_Email);
            EditText PassWord=findViewById(R.id.ActivityLogin_TextInputLayout_EditText_Password);
            String email=Email.getText().toString().trim();
            String password=PassWord.getText().toString().trim();
            Login(email,password);
            FirebaseAuth mAuth=FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user!=null)
            {
                Intent intent=new Intent(this,Activity_ReadUserProfile.class);
                startActivity(intent);

            }



        });

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
        if(id==R.id.NonHomeActivity_MenuItem_SearchBlood)
        {
            Intent intent=new Intent(this,Activity_SearchBlood.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    private void Login(String email,String password)
    {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Task<AuthResult> task)->{
                    if(!task.isSuccessful())
                    {
                        Log.i("Logined","Next,Inshallah");
                    }
                    else
                    {
                        Log.i("Logined","Alhadulliah");
                        showProfile();

                    }
                });
    }
    private void showProfile()
    {
        FirebaseAuth mAuth; mAuth=FirebaseAuth.getInstance();
        FirebaseUser  user = mAuth.getCurrentUser();

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
                                if(t!=null)
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