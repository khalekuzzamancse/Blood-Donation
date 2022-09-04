package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_Account_Delete extends AppCompatActivity {
    public static  String get_PassWord="null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_delete);

        Button del=findViewById(R.id.ActivityDeleteAccount_Button_Delete);
        Log.i("Password Received for delete", Activity_Account_Delete.get_PassWord);
        del.setOnClickListener(view -> {
            EditText Email=findViewById(R.id.ActivityDeleteAccount_EditText_Email);
            String email=Email.getText().toString().trim();
            EditText Pass=findViewById(R.id.ActivityDeleteAccount_TextInputLayout_EditText_Password);
            String pass=Pass.getText().toString().trim();
            FirebaseAuth auth=FirebaseAuth.getInstance();
            FirebaseUser currentUser=auth.getCurrentUser();
            if(pass.equals(get_PassWord)&&email.equals(currentUser.getEmail()))
            {
                ProgressBar p=findViewById(R.id.ActivityDelete_ProgressBar);
                p.setVisibility(View.VISIBLE);
                deleteAccount(email, pass);
            }
//insha-allah
        });



    }
    private void deleteAccount(String email,String password) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.i("Deleted", "User account deleted.");
                                            Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);
                                            Intent i=new Intent(Activity_Account_Delete.this,MainActivity.class);
                                            startActivity(i);
                                        }
                                        else
                                            Log.i("Deleted", "User account not deleted.");

                                    }
                                });

                    }
                });
    }

}