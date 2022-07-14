package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class worker_appointment_details extends AppCompatActivity {

    String AppID, Description, Address, Date_time, CustomerName, CustomerRating, WorkerID;
    TextView Nametv, Ratingtv, Addresstv, Date_timetv, Descriptiontv;
    EditText PriceEdit;
    Worker worker;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    Appointments CurrentAppointment;

    DatabaseReference workerref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_appointment_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            AppID = bundle.getString("AppID");
            Description = bundle.getString("Desc");
            Address = bundle.getString("Address");
            Date_time = bundle.getString("Date time");
            CustomerName = bundle.getString("Customer");
            CustomerRating = bundle.getString("Rating");
            WorkerID = bundle.getString("ID");
        }

        Nametv = (TextView) findViewById(R.id.clientnametext);
        Ratingtv = (TextView) findViewById(R.id.rating_text);
        Addresstv = (TextView) findViewById(R.id.address_text);
        Date_timetv = (TextView) findViewById(R.id.date_time);
        Descriptiontv = (TextView) findViewById(R.id.description_text);
        PriceEdit = (EditText) findViewById(R.id.priceEdit);

        Nametv.setText("Name: " + CustomerName);
        Ratingtv.setText("Rating: " + CustomerRating);
        Addresstv.setText("Address: " + Address);
        Date_timetv.setText("Date and time: " + Date_time);
        Descriptiontv.setText("Description: " + Description);


        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        workerref = databaseReference.child(values.workers_table).child(WorkerID);
        workerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                worker = snapshot.getValue(Worker.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button confirm = (Button) findViewById(R.id.confirm_btn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PriceEdit.getText().toString()))
                {
                    Toast.makeText(worker_appointment_details.this, "Price field is empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    DatabaseReference appointmentref = databaseReference.child(values.apps_table).child(AppID);
                    appointmentref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CurrentAppointment = snapshot.getValue(Appointments.class);
                            List<String> workers = CurrentAppointment.getRequested_workers();
                            if (workers == null)
                                workers = new ArrayList<>();
                            workers.add(WorkerID);
                            CurrentAppointment.setRequested_workers(workers);
                            List<Float> prices = CurrentAppointment.getRequested_price();
                            if (prices == null)
                                prices = new ArrayList<>();
                            prices.add(Float.parseFloat(PriceEdit.getText().toString()));
                            CurrentAppointment.setRequested_price(prices);
                            CurrentAppointment.setStatus(Statuss.worker_accepted);
                            appointmentref.setValue(CurrentAppointment);
                            Toast.makeText(worker_appointment_details.this, "Appointment request sent", Toast.LENGTH_SHORT).show();

                            List<String> workerapps = worker.getAppointmentsrequested();
                            if (workerapps == null)
                                workerapps = new ArrayList<>();

                            workerapps.add(AppID);
                            worker.setAppointmentsrequested(workerapps);

                            workerref.setValue(worker);

                            Intent gotoScreenVar = new Intent(worker_appointment_details.this, workers_main.class);

                            gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                            gotoScreenVar.putExtra("ID", WorkerID);
                            startActivity(gotoScreenVar);

//                            Intent intent = new Intent(worker_appointment_details.this, workers_main.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                            startActivity(intent);
//                        worker_appointment_details.this.finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }




            }
        });


    }
}