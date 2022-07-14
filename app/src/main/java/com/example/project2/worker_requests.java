package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recyclerView);
        listner = new ClickListner() {
            @Override
            public void click(int index, int choice){
                if (choice == 0)
                {

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
                        for (DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            Appointments appointment = snapshot1.getValue(Appointments.class);
                            DatabaseReference customerref = databaseReference.child(values.customers_table).child(appointment.getCustomerID());
                            customerref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    Customer customer = snapshot2.getValue(Customer.class);
                                    AppIDs.add(appointment.getAppID());
                                    Float rating = customer.getRating();
                                    list.add(new AppointListWorkerReq(customer.getName(), rating.toString() ,
                                            appointment.getDate() +"  at " +appointment.getTime(), appointment.getDescription()));
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

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