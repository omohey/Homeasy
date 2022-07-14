package com.example.project2;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentViewHolderWorkerReq extends RecyclerView.ViewHolder {
    TextView Name, Rating;
    TextView Date, Description;
    View view;
    Button details, dismiss;

    AppointmentViewHolderWorkerReq(View itemView)
    {
        super(itemView);
        Name
                = (TextView)itemView
                .findViewById(R.id.customerName);
        Rating
                = (TextView)itemView
                .findViewById(R.id.ratingtext);
        Date = (TextView) itemView.findViewById(R.id.datetext);
        Description = (TextView)itemView.findViewById(R.id.descriptiontext);
        details = (Button)itemView.findViewById(R.id.detailsbtn);
        dismiss = (Button)itemView.findViewById(R.id.dismissbtn) ;
        view  = itemView;
    }

}