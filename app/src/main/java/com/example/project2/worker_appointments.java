package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class worker_appointments extends AppCompatActivity {

    String ID;

    GalleryAdapterWorkerApps adapter;
    RecyclerView recyclerView;
    ClickListner listner;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference, workerref, appointmentsref;

    List<AppointListWorkerApps> list;
    List<String> acceptedappointmentsList, requestedappslist, allappointments;
    Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_appointments);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ID = bundle.getString("ID"); }

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        workerref = databaseReference.child(values.workers_table).child(ID);
        appointmentsref = databaseReference.child(values.apps_table);

        getData();

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recyclerView);
        listner = new ClickListner() {
            @Override
            public void click(int index){
                String AppID = allappointments.get(index);
                Intent i = new Intent(worker_appointments.this, worker_appointment_details2.class);
                i.putExtra("AppID", AppID);
                i.putExtra("WorkerID", ID);
                if (requestedappslist == null || index >= requestedappslist.size())
                    i.putExtra("status", "Con");
                else
                    i.putExtra("status", "Req");
                startActivity(i);
                //Toast.makeText(root.getContext(), "clicked item index is "+index,Toast.LENGTH_SHORT).show();
                /*Appointments app = appointmentsList.get(index);
                Intent i = new Intent(binding.getRoot().getContext(), customer_worker_requests.class);
                i.putExtra("AppID", app.getAppID());
                startActivity(i);*/
            }
        };
        adapter
                = new GalleryAdapterWorkerApps(
                list, this,listner);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));



    }
    public void getData()
    {
        list = new ArrayList<>();
        allappointments = new ArrayList<>();
//        acceptedappointmentsList = new ArrayList<>();
//        requestedappslist = new ArrayList<>();

        workerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                worker = snapshot.getValue(Worker.class);
                acceptedappointmentsList = worker.getAppointments();
                requestedappslist = worker.getAppointmentsrequested();

                if (requestedappslist == null && acceptedappointmentsList == null)
                {
                    Toast.makeText(worker_appointments.this, "You have no appointments", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (requestedappslist == null)
                {

                }
                else
                {
                    for (String x : requestedappslist)
                    {
                        appointmentsref.child(x).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Appointments app = snapshot.getValue(Appointments.class);
                                String CustomerID, Date, Time, Address;
                                CustomerID = app.getCustomerID();
                                Date = app.getDate();
                                Time = app.getTime();
                                Address = app.getAddress();
                                getcustomername_andupdate(CustomerID, Date, Time, Address, app.getAppID());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                if (acceptedappointmentsList == null)
                {

                }
                else
                {
                    for (String x : acceptedappointmentsList)
                    {
                        appointmentsref.child(x).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Appointments app = snapshot.getValue(Appointments.class);
                                String CustomerID, Date, Time, Address;
                                CustomerID = app.getCustomerID();
                                Date = app.getDate();
                                Time = app.getTime();
                                Address = app.getAddress();
                                getcustomername_andupdate(CustomerID, Date, Time, Address, app.getAppID());
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

    public void getcustomername_andupdate(String CustomerID, String Date, String Time, String Address, String AppID)
    {
        DatabaseReference customerref = databaseReference.child(values.customers_table).child(CustomerID);
        customerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Customer customer = snapshot.getValue(Customer.class);
                String customername = customer.getName();
                AppointListWorkerApps appdisplay = new AppointListWorkerApps(customername, Date, Time, Address);
                list.add(appdisplay);
                allappointments.add(AppID);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}