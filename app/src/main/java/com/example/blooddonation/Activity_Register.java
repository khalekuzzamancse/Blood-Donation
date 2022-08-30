package com.example.blooddonation;

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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Activity_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_scrolable);

        Toolbar toolbar =findViewById(R.id.NonHomeActivity_Toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");

        Button submit=findViewById(R.id.Activity_Register_Button_Submit);
        submit.setOnClickListener(view -> {
          SetUserInfo();
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

    private void Register(String email,String password)
    {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener((Task<AuthResult> task)->{
                    if(!task.isSuccessful())
                    {
                        Log.i("Logined","Next,Inshallah");
                    }
                    else
                    {
                        Log.i("Logined","Alhadulliah");
                        Intent intent=new Intent(this,MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
    private void SetUserInfo()
    {
        EditText Name=findViewById(R.id.Activity_Register_TextInputLayout_EditText_Name);
        String name=Name.getText().toString().trim();
        EditText Email=findViewById(R.id.Activity_Register_TextInputLayout_EditText_Email);
        String email=Email.getText().toString().trim();
        EditText PhoneNumber=findViewById(R.id.Activity_Register_TextInputLayout_EditText_PhoneNumber);
        String phoneNumber=PhoneNumber.getText().toString().trim();
        EditText UserName=findViewById(R.id.Activity_Register_TextInputLayout_EditText_UserName);
        String userName=UserName.getText().toString().trim();
        EditText PassWord=findViewById(R.id.Activity_Register_TextInputLayout_EditText_Password);
        String password=PassWord.getText().toString().trim();
        EditText ConfirmPassWord=findViewById(R.id.Activity_Register_TextInputLayout_EditText_ConfirmPassword);
        String confirmPassword=ConfirmPassWord.getText().toString().trim();
        Log.i("Alhamdulillah\n",name+"\n"+email+"\n"+phoneNumber+"\n"+userName+"\n"+password+"\n"+confirmPassword);

    }

}