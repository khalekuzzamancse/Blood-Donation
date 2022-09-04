package com.example.blooddonation;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Activity_Login extends AppCompatActivity {
private   ProgressBar p;
    Button register;
    Toolbar toolbar;
public static String Extra_Login="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_scrolable);


       toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");


        register=findViewById(R.id.ActivityLogin_Button_Register);
        register.setOnClickListener((view)->{
            Intent intent=new Intent(this,Activity_Register.class);
            startActivity(intent);
        });
        Button login=findViewById(R.id.ActivityLogin_Button_Login);
        login.setOnClickListener(view -> {

            p=findViewById(R.id.ActivityLogin_ProgessBar);
          p.setVisibility(View.VISIBLE);
            EditText Email=findViewById(R.id.ActivityLogin_EditText_Email);
            EditText PassWord=findViewById(R.id.ActivityLogin_TextInputLayout_EditText_Password);
            String email=Email.getText().toString().trim();
            String password=PassWord.getText().toString().trim();
            Login(email,password);
        });
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



    private void Login(String email,String password)
    {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Task<AuthResult> task)->{
                    if(!task.isSuccessful())
                    {
                        //Log.i("Curr Logined","Next,Inshallah");
                        ;
                    }
                    else
                    {

                       // Log.i("Curr Logined","Alhaumdulliah");
                        FirebaseUser user = mAuth.getCurrentUser();
                       p.setVisibility(View.INVISIBLE);
                        Intent i= new Intent(this, MainActivity.class);
                        i.putExtra(MainActivity.Extra_Login,"FromLogin");
                        startActivity(i);


                    }
                });
    }

    @Override
    protected void onResume() {
        Intent intent=getIntent();
        Intent i=getIntent();
        String s= i.getStringExtra(MainActivity.Extra_Login);
        if(s!=null&&s.equals("FromRegister"))
        {
            Snackbar.make(toolbar,"Registered Successfully",Snackbar.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}