package com.example.project2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentViewHolder  extends RecyclerView.ViewHolder {
    TextView WorkerName;
    TextView Price;
    TextView Rating;
    TextView Jobtype;
    View view;

    AppointmentViewHolder(View itemView)
    {
        super(itemView);
        WorkerName
                = (TextView)itemView
                .findViewById(R.id.workerName);
        Rating
                = (TextView)itemView
                .findViewById(R.id.ratingtext);
        Price
                = (TextView)itemView
                .findViewById(R.id.pricetext);
        Jobtype = (TextView) itemView.findViewById(R.id.jobtypetext);
        view  = itemView;
    }

}