package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class customer_worker_requests extends AppCompatActivity {

    TextView jobtype, date, address, jobdescription;

    String AppID;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    Appointments appointment;

    GalleryAdapterRequests adapter;
    RecyclerView recyclerView;
    ClickListner listner;

    List<AppointListRequests> list;

//    List<Appointments> appointmentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_worker_requests);

        jobtype = (TextView) findViewById(R.id.jobtypetextview);
        date = (TextView) findViewById(R.id.datetextview);
        address = (TextView) findViewById(R.id.addresstextview);
        jobdescription = (TextView) findViewById(R.id.jobdescriptiontextview);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            AppID = bundle.getString("AppID");
        }

        list = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recyclerView);
        listner = new ClickListner() {
            @Override
            public void click(int index, int choice){
                //Toast.makeText(root.getContext(), "clicked item index is "+index,Toast.LENGTH_SHORT).show();

            }
        };
        adapter
                = new GalleryAdapterRequests(
                list, customer_worker_requests.this,listner);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));



        DatabaseReference appointsref = databaseReference.child(values.apps_table).child(AppID);
        appointsref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointment = snapshot.getValue(Appointments.class);
                jobtype.setText("Job Type: "+ appointment.getJobType());
                date.setText("Date: "+ appointment.getDate() + "      Time: "+ appointment.getTime());
                address.setText("Address: "+appointment.getAddress());
                jobdescription.setText("Job description: " + appointment.getDescription());

                List<String> workersRequested = appointment.getRequested_workers();
                List<Float> requestedprices = appointment.getRequested_price();
                if (workersRequested == null)
                {
                    Toast.makeText(customer_worker_requests.this, "No workers accepted yet",Toast.LENGTH_LONG).show();
                }
                else
                {
                    for (String workerid : workersRequested)
                    {
                        databaseReference.child(values.workers_table).child(workerid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Worker worker = snapshot.getValue(Worker.class);
                                Float price = requestedprices.get(workersRequested.indexOf(worker.getID()));
                                Float rating = worker.getRating();
                                AppointListRequests appdisplay = new AppointListRequests(worker.getName(), rating.toString() ,price.toString());
                                list.add(appdisplay);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}