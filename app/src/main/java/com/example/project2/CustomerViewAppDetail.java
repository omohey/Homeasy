package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomerViewAppDetail extends AppCompatActivity {

    String AppID;
    Appointments CurrentAppointment;
    TextView WorkerName, Rating, JobType, Date, Description, Price, Rate_Rated, Phone;
    RatingBar ratingBar;
    Button Ratebtn, reportButton;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;


    DatabaseReference appoinmentref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_app_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            AppID = bundle.getString("AppID");
        }
        WorkerName = (TextView) findViewById(R.id.workerName);
        Rating = (TextView) findViewById(R.id.rating_text);
        JobType = (TextView) findViewById(R.id.jobtype);
        Date = (TextView) findViewById(R.id.date_time);
        Description = (TextView) findViewById(R.id.description_text);
        Price = (TextView) findViewById(R.id.price_text);
        Rate_Rated = (TextView) findViewById(R.id.rate_rated);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Ratebtn = (Button) findViewById(R.id.ratebtn);
        reportButton = (Button) findViewById(R.id.reportButton);
        Phone = (TextView) findViewById(R.id.phonetext);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();




        appoinmentref = databaseReference.child(values.apps_table).child(AppID);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerViewAppDetail.this, ReportPage.class);
                i.putExtra("appID", AppID);
                i.putExtra("reportedID", CurrentAppointment.getWorkerID());
                i.putExtra("reporterID", CurrentAppointment.getCustomerID());
                i.putExtra("type", "C");
                startActivity(i);
            }
        });
        Ratebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float rating  = ratingBar.getRating();
                int ratingint = rating.intValue();
                if (ratingint == 0)
                {
                    Toast.makeText(CustomerViewAppDetail.this, "Please enter a rating of atleast 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    appoinmentref.child("customerRated").setValue(true);
                    appoinmentref.child("workergotrated").setValue(ratingint);
                    DatabaseReference workerref = databaseReference.child(values.workers_table).child(CurrentAppointment.getWorkerID());
                    workerref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Worker worker = snapshot.getValue(Worker.class);
                            float workerrating = worker.getRating();
                            int norated = worker.getNorated();
                            float newrating = (workerrating * norated + ratingint)/(norated+1);
                            norated = norated+1;
                            workerref.child("norated").setValue(norated);
                            workerref.child("rating").setValue(newrating);
                            refresh();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });


        refresh();
    }

    public Boolean isDatePassed(int day, int month, int year)
    {
//        java.util.Date date = new Date()
        Calendar calendar = new GregorianCalendar(year, month-1, day);
        Calendar today = Calendar.getInstance();

        if (calendar.after(today))
            return false;
        return true;
    }

    public  void refresh()
    {
        appoinmentref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CurrentAppointment = snapshot.getValue(Appointments.class);
                String jobtype = CurrentAppointment.getJobType();
                String date = CurrentAppointment.getDate() + " at " + CurrentAppointment.getTime();
                String description = CurrentAppointment.getDescription();
                String price = Integer.toString(CurrentAppointment.getPrice());
                JobType.setText("Job type: " +jobtype);
                Date.setText("Date and time: " + date);
                Description.setText("Description: " + description);
                Price.setText("Price: " +price);
                if (CurrentAppointment.getStatus().equals(Statuss.completed))
                {
                    if (CurrentAppointment.customerRated == false)
                    {
                        Rate_Rated.setText("Rate:");
                        Rate_Rated.setVisibility(View.VISIBLE);
                        ratingBar.setVisibility(View.VISIBLE);
                        Ratebtn.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        Rate_Rated.setText("Rated:");
                        Rate_Rated.setVisibility(View.VISIBLE);
                        ratingBar.setVisibility(View.VISIBLE);
                        ratingBar.setRating(CurrentAppointment.getWorkergotrated());
                        ratingBar.setEnabled(false);
                        Ratebtn.setVisibility(View.VISIBLE);
                        Ratebtn.setEnabled(false);
                    }
                }
                else if (isDatePassed(CurrentAppointment.getDay(), CurrentAppointment.getMonth(), CurrentAppointment.getYear()))
                {
                    appoinmentref.child("status").setValue(Statuss.completed);
                    CurrentAppointment.setStatus(Statuss.completed);
                    Rate_Rated.setText("Rate:");
                    Rate_Rated.setVisibility(View.VISIBLE);
                    ratingBar.setVisibility(View.VISIBLE);
                    Ratebtn.setVisibility(View.VISIBLE);
                }
                DatabaseReference workerref = databaseReference.child(values.workers_table).child(CurrentAppointment.workerID);
                workerref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Worker worker = snapshot.getValue(Worker.class);
                        WorkerName.setText("Name: "+ worker.getName());
                        Float rating = worker.getRating();
                        Rating.setText("Rating: " + rating.toString());
                        Phone.setText("Phone: " + worker.getPhone());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}