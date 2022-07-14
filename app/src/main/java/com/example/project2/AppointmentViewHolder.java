package com.example.project2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentViewHolder  extends RecyclerView.ViewHolder {
    TextView WorkerName;
    TextView Date;
    TextView Time;
    TextView Jobtype;
    View view;

    AppointmentViewHolder(View itemView)
    {
        super(itemView);
        WorkerName
                = (TextView)itemView
                .findViewById(R.id.workerName);
        Date
                = (TextView)itemView
                .findViewById(R.id.date_text);
        Time
                = (TextView)itemView
                .findViewById(R.id.time_text);
        Jobtype = (TextView) itemView.findViewById(R.id.jobtypetext);
        view  = itemView;
    }

}