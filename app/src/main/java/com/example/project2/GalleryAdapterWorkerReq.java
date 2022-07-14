package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class GalleryAdapterWorkerReq extends RecyclerView.Adapter<AppointmentViewHolderWorkerReq> {

    List<AppointListWorkerReq> list
            = Collections.emptyList();

    Context context;
    ClickListner listner;

    public GalleryAdapterWorkerReq(List<AppointListWorkerReq> list,
                                  Context context, ClickListner listiner)
    {
        this.list = list;
        this.context = context;
        this.listner = listiner;
    }

    @Override
    public AppointmentViewHolderWorkerReq
    onCreateViewHolder(ViewGroup parent,
                       int viewType)
    {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.oneitemworker_requests,
                        parent, false);

        AppointmentViewHolderWorkerReq viewHolder
                = new AppointmentViewHolderWorkerReq(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final AppointmentViewHolderWorkerReq viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.Name
                .setText(list.get(position).Name);
        viewHolder.Rating
                .setText(list.get(position).Rating);
        viewHolder.Date
                .setText(list.get(position).Date);
        viewHolder.Description
                .setText(list.get(position).Description);
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.click(index, 0);
            }
        });
        viewHolder.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.click(index, 1);
            }
        });
        /*viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listner.click(index);
            }
        });*/
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }


}