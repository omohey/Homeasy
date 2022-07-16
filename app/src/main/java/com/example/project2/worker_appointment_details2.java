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

import com.example.project2.databinding.ActivityWorkerAppointmentDetails2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class worker_appointment_details2 extends AppCompatActivity {
    String AppID;
    Appointments CurrentAppointment;
    TextView CustomerName, Rating, Status, Date, Description, Price, Rate_Rated, Phone;
    RatingBar ratingBar;
    Button Ratebtn, reportButton;
    String Req_Con;
    String WorkerID;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;


    DatabaseReference appoinmentref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_appointment_details2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            AppID = bundle.getString("AppID");
            Req_Con = bundle.getString("status");
            WorkerID = bundle.getString("WorkerID");
        }
        CustomerName = (TextView) findViewById(R.id.customerName);
        Rating = (TextView) findViewById(R.id.rating_text);
        Status = (TextView) findViewById(R.id.status);
        Date = (TextView) findViewById(R.id.date_time);
        Description = (TextView) findViewById(R.id.description_text);
        Price = (TextView) findViewById(R.id.price_text);
        Rate_Rated = (TextView) findViewById(R.id.rate_rated);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Ratebtn = (Button) findViewById(R.id.ratebtn);
        Phone = (TextView) findViewById(R.id.phone_text);
        reportButton = (Button) findViewById(R.id.reportButtonWorker);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();




        appoinmentref = databaseReference.child(values.apps_table).child(AppID);

        if (Req_Con.equals("Req"))
        {
            reportButton.setVisibility(View.INVISIBLE);
        }


        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(worker_appointment_details2.this, ReportPage.class);
                i.putExtra("appID", AppID);
                i.putExtra("reportedID", CurrentAppointment.getCustomerID());
                i.putExtra("reporterID", CurrentAppointment.getWorkerID());
                i.putExtra("type", "W");
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
                    Toast.makeText(worker_appointment_details2.this, "Please enter a rating of atleast 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    appoinmentref.child("workerRated").setValue(true);
                    appoinmentref.child("customergotrated").setValue(ratingint);
                    DatabaseReference customerref = databaseReference.child(values.customers_table).child(CurrentAppointment.getCustomerID());
                    customerref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Customer customer = snapshot.getValue(Customer.class);
                            float customerrating = customer.getRating();
                            int norated = customer.getNorated();
                            float newrating = (customerrating * norated + ratingint)/(norated+1);
                            norated = norated+1;
                            customerref.child("norated").setValue(norated);
                            customerref.child("rating").setValue(newrating);
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
                String status;
                if (Req_Con.equals("Req"))
                    status = "Requested";
                else
                    status = "Confirmed";
                String date = CurrentAppointment.getDate() + " at " + CurrentAppointment.getTime();
                String description = CurrentAppointment.getDescription();
                String price = "";
                if (status.equals("Requested"))
                {
                    List<Float> prices = CurrentAppointment.getRequested_price();
                    List<String> workers = CurrentAppointment.getRequested_workers();
                    price = Float.toString(prices.get(workers.indexOf(WorkerID)));

                }
                else
                {
                    price = Integer.toString(CurrentAppointment.getPrice());
                }


                Status.setText("Status: " + status);
                Date.setText("Date and time: " + date);
                Description.setText("Description: " + description);
                Price.setText("Price: " + price);
                if (CurrentAppointment.getStatus().equals(Statuss.completed))
                {
                    status = "Completed";
                    Status.setText("Status: " + status);
                    if (CurrentAppointment.getWorkerRated() == false)
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
                        ratingBar.setRating(CurrentAppointment.getCustomergotrated());
                        ratingBar.setEnabled(false);
                        Ratebtn.setVisibility(View.VISIBLE);
                        Ratebtn.setEnabled(false);
                    }
                }
                else if (isDatePassed(CurrentAppointment.getDay(), CurrentAppointment.getMonth(), CurrentAppointment.getYear()))
                {
                    appoinmentref.child("status").setValue(Statuss.completed);
                    status = "Completed";
                    Status.setText("Status: " + status);
                    CurrentAppointment.setStatus(Statuss.completed);
                    Rate_Rated.setText("Rate:");
                    Rate_Rated.setVisibility(View.VISIBLE);
                    ratingBar.setVisibility(View.VISIBLE);
                    Ratebtn.setVisibility(View.VISIBLE);
                }
                DatabaseReference customerref = databaseReference.child(values.customers_table).child(CurrentAppointment.getCustomerID());
                customerref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Customer customer = snapshot.getValue(Customer.class);
                        CustomerName.setText("Name: "+ customer.getName());
                        Float rating = customer.getRating();
                        Rating.setText("Rating: " + rating.toString());
                        if (Req_Con.equals("Req"))
                            Phone.setText("Phone: *currently unavailable");
                        else
                            Phone.setText("Phone: " + customer.getPhone());
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