package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class customer_booking extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton, timebutton, confirmbtn;
    private String jobtype, Address, JobDesc;
    EditText AddressEdit, JobDescEdit;
    int hour, min;
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking);


        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        jobtype = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jobtype = bundle.getString("Job"); }


        TextView tv = (TextView) findViewById(R.id.jobtype);
        tv.setText(tv.getText()+ " " +jobtype);

        timebutton = findViewById(R.id.timebutton);

        confirmbtn = (Button) findViewById(R.id.confirm_bookingbtn);

        AddressEdit = (EditText) findViewById(R.id.AddressText);
        JobDescEdit = (EditText) findViewById(R.id.JobDescText);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address = AddressEdit.getText().toString();
                JobDesc = JobDescEdit.getText().toString();

                if (TextUtils.isEmpty(Address))
                {
                    Toast.makeText(customer_booking.this, "Address field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(JobDesc))
                {
                    Toast.makeText(customer_booking.this, "Job Decription field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteofday) {
                hour = hourOfDay;
                min = minuteofday;
                timebutton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));

            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, listener, hour, min, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}








