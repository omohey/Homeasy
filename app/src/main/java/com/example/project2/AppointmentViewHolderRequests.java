package com.example.project2;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AppointmentViewHolderRequests  extends RecyclerView.ViewHolder {
    TextView Name, Rating;
    TextView Price;
    View view;
    Button confirm, decline;

    AppointmentViewHolderRequests(View itemView)
    {
        super(itemView);
        Name
                = (TextView)itemView
                .findViewById(R.id.workerName);
        Rating
                = (TextView)itemView
                .findViewById(R.id.ratingtext);
        Price = (TextView) itemView.findViewById(R.id.pricetext);
        confirm = (Button)itemView.findViewById(R.id.confirmbtn);
        decline = (Button)itemView.findViewById(R.id.declinebtn) ;
        view  = itemView;
    }

}