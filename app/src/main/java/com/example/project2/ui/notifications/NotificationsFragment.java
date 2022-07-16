package com.example.project2.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.AppointListData;
import com.example.project2.AppointListPending;
import com.example.project2.Appointments;
import com.example.project2.ClickListner;
import com.example.project2.Customer;
import com.example.project2.CustomerViewAppDetail;
import com.example.project2.GalleryAdapterPending;
import com.example.project2.ImageGalleryAdapter2;
import com.example.project2.R;
import com.example.project2.Statuss;
import com.example.project2.Worker;
import com.example.project2.customer_main;
import com.example.project2.customer_worker_requests;
import com.example.project2.databinding.FragmentNotificationsBinding;
import com.example.project2.values;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.Job;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    ImageGalleryAdapter2 adapter;
    RecyclerView recyclerView;
    ClickListner listner;
    String customerID;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    List<AppointListData> list;
    List<String> appointmentsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //NotificationsViewModel notificationsViewModel =
                //new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        customer_main activity = (customer_main) getActivity();
        customerID = activity.getCustomerID();

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference();

        getData();

        recyclerView
                = (RecyclerView)root.findViewById(
                R.id.recyclerView);
        listner = new ClickListner() {
            @Override
            public void click(int index){
                String AppID = appointmentsList.get(index);
                Intent i = new Intent(binding.getRoot().getContext(), CustomerViewAppDetail.class);
                i.putExtra("AppID", AppID);
                startActivity(i);
                //Toast.makeText(root.getContext(), "clicked item index is "+index,Toast.LENGTH_SHORT).show();
                /*Appointments app = appointmentsList.get(index);
                Intent i = new Intent(binding.getRoot().getContext(), customer_worker_requests.class);
                i.putExtra("AppID", app.getAppID());
                startActivity(i);*/
            }
        };
        adapter
                = new ImageGalleryAdapter2(
                list, root.getContext(),listner);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(root.getContext()));



        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        /*
        ListView lv = (ListView) root.findViewById(R.id.completed);
        String[] values= new String[] {"Appointment 1\nDate 1", "Appointment 2\nDate 2", "Appointment 3\nDate 3", "Appointment 4\nDate4",
                "Appointment 5\nDate 5", "Appointment 6\nDate 6", "Appointment 7\nDate 7",
                "Appointment 8\nDate 8", "Appointment 9\nDate 9", "Appointment 10\nDate 10", "Appointment 11\nDate11",
                "Appointment 12\nDate 12", "Appointment 13\nDate 13"};
        ArrayAdapter arrayAdapter= new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1,values);
        lv.setAdapter(arrayAdapter);

        ListView list = (ListView) root.findViewById(R.id.inprogress);

        ArrayAdapter arrayAdapter2= new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1,values);
        list.setAdapter(arrayAdapter2);*/



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getData()
    {
        list = new ArrayList<>();
        appointmentsList = new ArrayList<>();
        DatabaseReference customerref = databaseReference.child(values.customers_table).child(customerID);

        customerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null)
                {
                    Toast.makeText(binding.getRoot().getContext(), "Error", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Customer customer = snapshot.getValue(Customer.class);
                    List<String> list1 = customer.getAppointments();
                    if (list1 == null)
                    {
                        Toast.makeText(binding.getRoot().getContext(), "No appointments", Toast.LENGTH_SHORT).show();
                        return;
//                        list1 = new ArrayList<>();
                    }
                    else
                    {
                        DatabaseReference appointref = databaseReference.child(values.apps_table);
                        for (String appoint : list1)
                        {
                            appointref.child(appoint).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Appointments app = snapshot.getValue(Appointments.class);
                                    if (app.getStatus().equals(Statuss.customer_accepted) || app.getStatus().equals(Statuss.completed) )
                                    {
                                        String WorkerID, Date, Time, JobType;
                                        WorkerID = app.getWorkerID();
                                        Date = app.getDate();
                                        Time = app.getTime();
                                        JobType = app.getJobType();
                                        getworkername_andupdate(WorkerID, Date, Time, JobType, app.getAppID());

                                    }



                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }



//                            Intent i = new Intent(customer_booking.this, DashboardFragment.class);
//                            i.putExtra("ID", customerID);
//                            startActivity(i);


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getworkername_andupdate(String WorkerID, String Date, String Time, String JobType, String AppID)
    {
        DatabaseReference workerref = databaseReference.child(values.workers_table).child(WorkerID);
        workerref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Worker worker = snapshot.getValue(Worker.class);
                String workername = worker.getName();
                AppointListData appdisplay = new AppointListData(workername, Date, Time, JobType);
                list.add(appdisplay);
                appointmentsList.add(AppID);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}