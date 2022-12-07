package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blooddonation.database.CallbackResult;
import com.example.blooddonation.database.FirebaseAuthCustom;
import com.example.blooddonation.database.WritingDocument;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateDonateDateActvity extends AppCompatActivity {
    private TextView tvSelectDate;
    private EditText etSelectDate;
    int days = 0;
    Button save;
    private CallbackResult callbackResult = new CallbackResult() {

        @Override
        public void isSuccess(boolean response) {
            if (response)
                showSnackBar("Updated Successfully");
            else
                showSnackBar("Failed");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_donate_date_activity);
        tvSelectDate = findViewById(R.id.tvSelectDate);
        etSelectDate = findViewById(R.id.select_date_ET);
        save = findViewById(R.id.saveBTN);


        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(UpdateDonateDateActvity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        days = month * 30 + year * 365 + dayOfMonth;

                        etSelectDate.setText(String.valueOf(dayOfMonth + "-" + month + "-" + year));

                    }
                }, year, month, day);
                dialog.show();


            }
        });

        save.setOnClickListener(view -> {
            days = days + 90;
            HashMap<String, Object> data = new HashMap<>();
            String nextDate=String.valueOf(days);
            data.put("nextDonateDate",nextDate);
            WritingDocument document = new WritingDocument();
            document.updateDocument(new FirebaseAuthCustom().getUserEmail(), data, callbackResult);
        });

    }

    void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar
                .make(etSelectDate, msg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.purple_500));
        snackbar.show();
    }

}