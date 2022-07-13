package com.example.project2;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentViewHolderPending  extends RecyclerView.ViewHolder {
    TextView Date, Description;
    TextView Jobtype;
    View view;

    AppointmentViewHolderPending(View itemView)
    {
        super(itemView);
        Date
                = (TextView)itemView
                .findViewById(R.id.ratingtext);
        Description
                = (TextView)itemView
                .findViewById(R.id.pricetext);
        Jobtype = (TextView) itemView.findViewById(R.id.jobtypetext);
        view  = itemView;
    }

}