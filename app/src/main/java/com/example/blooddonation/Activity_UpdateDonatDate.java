package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;

public class Activity_UpdateDonatDate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_donat_date);
        DatePickerDialog dialog = new DatePickerDialog(this);
        EditText editText = findViewById(R.id.UpdateDoanteDate);
        editText.setOnClickListener(view -> {
            dialog.show();
        });


    }
}