package com.example.project2;

import android.location.Location;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentViewHolderWorkerApps extends RecyclerView.ViewHolder {
    TextView WorkerName;
    TextView Date;
    TextView Time;
    TextView Location;
    View view;

    AppointmentViewHolderWorkerApps(View itemView)
    {
        super(itemView);
        WorkerName
                = (TextView)itemView
                .findViewById(R.id.customerName);
        Date
                = (TextView)itemView
                .findViewById(R.id.date_text);
        Time
                = (TextView)itemView
                .findViewById(R.id.time_text);
        Location = (TextView) itemView.findViewById(R.id.location_text);
        view  = itemView;
    }

}