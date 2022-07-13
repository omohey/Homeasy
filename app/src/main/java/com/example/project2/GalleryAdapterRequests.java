package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class GalleryAdapterRequests extends RecyclerView.Adapter<AppointmentViewHolderRequests> {

    List<AppointListRequests> list
            = Collections.emptyList();

    Context context;
    ClickListner listner;

    public GalleryAdapterRequests(List<AppointListRequests> list,
                                 Context context, ClickListner listiner)
    {
        this.list = list;
        this.context = context;
        this.listner = listiner;
    }

    @Override
    public AppointmentViewHolderRequests
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
                .inflate(R.layout.oneitemrequests,
                        parent, false);

        AppointmentViewHolderRequests viewHolder
                = new AppointmentViewHolderRequests(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final AppointmentViewHolderRequests viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.Name
                .setText(list.get(position).Name);
        viewHolder.Rating
                .setText(list.get(position).Rating);
        viewHolder.Price
                .setText(list.get(position).Price);
        viewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.click(index, 0);
            }
        });
        viewHolder.decline.setOnClickListener(new View.OnClickListener() {
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