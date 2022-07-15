package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class customer_worker_requests extends AppCompatActivity {

    TextView jobtype, date, address, jobdescription;

    String AppID, CustomerID;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    Appointments appointment;

    GalleryAdapterRequests adapter;
    RecyclerView recyclerView;
    ClickListner listner;

    List<AppointListRequests> list;

    List<String> workersRequested;
    List<Float> requestedprices;

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
            CustomerID = bundle.getString("CustID");
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
                if (choice == 0)
                {
                    accept(index);
                }
                else
                {
                    decline(index);
                }

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

                workersRequested = appointment.getRequested_workers();
                requestedprices = appointment.getRequested_price();
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

    public void decline(int index)
    {
        String WorkerID = workersRequested.get(index);
        workersRequested.remove(index);
        list.remove(index);
        requestedprices.remove(index);
        DatabaseReference appointmentref = databaseReference.child(values.apps_table).child(AppID);
        appointmentref.child("requested_price").setValue(requestedprices);
        appointmentref.child("requested_workers").setValue(workersRequested);
        if (workersRequested.size() == 0)
        {
            appointmentref.child("status").setValue(Statuss.requested);
        }

        DatabaseReference workerref = databaseReference.child(values.workers_table).child(WorkerID);
        workerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Worker worker = snapshot.getValue(Worker.class);
                List<String> workerapps = worker.getAppointmentsrequested();
                workerapps.remove(workerapps.indexOf(AppID));
                workerref.child("appointmentsrequested").setValue(workerapps);
                customer_worker_requests.this.finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void accept(int index)
    {
        String WorkerID = workersRequested.get(index);
        int price = requestedprices.get(index).intValue();

        DatabaseReference appointmentref = databaseReference.child(values.apps_table).child(AppID);
        appointmentref.child("workerID").setValue(WorkerID);
        appointmentref.child("status").setValue(Statuss.customer_accepted);
        appointmentref.child("price").setValue(price);

        DatabaseReference workersref = databaseReference.child(values.workers_table);
        workersref.child(WorkerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Worker worker = snapshot.getValue(Worker.class);
                List<String> workerapps = worker.getAppointmentsrequested();
                List<String> workeracceptedapps = worker.getAppointments();
                if (workeracceptedapps == null)
                    workeracceptedapps = new ArrayList<>();
                workerapps.remove(workerapps.indexOf(AppID));
                workeracceptedapps.add(AppID);
                workersref.child(WorkerID).child("appointments").setValue(workeracceptedapps);
                workersref.child(WorkerID).child("appointmentsrequested").setValue(workerapps);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        workersRequested.remove(index);

        for (String x : workersRequested)
        {
            workersref.child(x).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Worker worker = snapshot.getValue(Worker.class);
                    List<String> workerapps = worker.getAppointmentsrequested();
                    workerapps.remove(workerapps.indexOf(AppID));
                    workersref.child(x).child("appointmentsrequested").setValue(workerapps);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
//        Intent intent = new Intent(customer_worker_requests.this, HomeFragment.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        Intent gotoScreenVar = new Intent(customer_worker_requests.this, customer_main.class);

        gotoScreenVar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        gotoScreenVar.putExtra("ID", CustomerID);
        startActivity(gotoScreenVar);
    }
}