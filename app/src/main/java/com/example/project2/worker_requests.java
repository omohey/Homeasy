package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class worker_requests extends AppCompatActivity {

    String ID;

    Worker worker;
    GalleryAdapterWorkerReq adapter;
    RecyclerView recyclerView;
    ClickListner listner;
    TextView jobsearch;


    List<AppointListWorkerReq> list;
    List<String> AppIDs;
    List<Appointments> Appointments_searched;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_requests);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ID = bundle.getString("ID");
        }

        jobsearch = (TextView) findViewById(R.id.jobsearchtext);

        FirebaseDatabase firebaseDatabase;

        DatabaseReference databaseReference;

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        list = new ArrayList<>();
        AppIDs = new ArrayList<>();
        Appointments_searched = new ArrayList<>();

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recyclerView);
        listner = new ClickListner() {
            @Override
            public void click(int index, int choice){
                if (choice == 0)
                {
                    Appointments app = Appointments_searched.get(index);
                    AppointListWorkerReq app2 = list.get(index);
                    Intent i = new Intent(worker_requests.this, worker_appointment_details.class);
                    i.putExtra("AppID", app.getAppID());
                    i.putExtra("Desc", app.getDescription());
                    i.putExtra("Address", app.getAddress());
                    i.putExtra("Date time", app2.getDate());
                    i.putExtra("Customer", app2.getName());
                    i.putExtra("Rating", app2.getRating());
                    i.putExtra("ID", ID);

                    startActivity(i);

                }
                else
                {
                    list.remove(index);
                    adapter.notifyDataSetChanged();

                }
                //Toast.makeText(root.getContext(), "clicked item index is "+index,Toast.LENGTH_SHORT).show();

            }
        };
        adapter
                = new GalleryAdapterWorkerReq(
                list, worker_requests.this,listner);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        DatabaseReference workerref = databaseReference.child(values.workers_table).child(ID);
        workerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                worker = snapshot.getValue(Worker.class);
                jobsearch.setText("Job search: " + values.getjobstring(worker.getJobType()) );

                DatabaseReference appointsearchref = databaseReference.child(values.apps_table);
                appointsearchref.orderByChild("jobType").equalTo(values.getjobstring(worker.getJobType())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.getValue() == null)
                        {
                            Toast.makeText(worker_requests.this, "There are currently no requests available", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            Appointments appointment = snapshot1.getValue(Appointments.class);
                            List<String> workers = appointment.getRequested_workers();
                            Boolean flag = true;
                            if (workers == null)
                            {
                                flag = true;
                            }
                            else
                            {
                                for (String x : workers)
                                    if (x.equals(ID))
                                        flag = false;
                            }
                            if ( flag && (appointment.getStatus().equals(Statuss.requested) || appointment.getStatus().equals(Statuss.worker_accepted) ) )
                            {
                                DatabaseReference customerref = databaseReference.child(values.customers_table).child(appointment.getCustomerID());
                                customerref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        Customer customer = snapshot2.getValue(Customer.class);
                                        AppIDs.add(appointment.getAppID());
                                        Float rating = customer.getRating();
                                        list.add(new AppointListWorkerReq(customer.getName(), rating.toString() ,
                                                appointment.getDate() +"  at " +appointment.getTime(), appointment.getDescription()));
                                        Appointments_searched.add(appointment);
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
                //userref.orderByChild("username").equalTo(Username).addListenerForSingleValueEvent
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }
}