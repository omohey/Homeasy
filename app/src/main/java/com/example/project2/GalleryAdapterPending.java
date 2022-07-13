package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class GalleryAdapterPending extends RecyclerView.Adapter<AppointmentViewHolderPending> {

    List<AppointListPending> list
            = Collections.emptyList();

    Context context;
    ClickListner listner;

    public GalleryAdapterPending(List<AppointListPending> list,
                                Context context, ClickListner listiner)
    {
        this.list = list;
        this.context = context;
        this.listner = listiner;
    }

    @Override
    public AppointmentViewHolderPending
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
                .inflate(R.layout.oneitempending,
                        parent, false);

        AppointmentViewHolderPending viewHolder
                = new AppointmentViewHolderPending(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final AppointmentViewHolderPending viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.Jobtype
                .setText(list.get(position).jobtype);
        viewHolder.Date
                .setText(list.get(position).date);
        viewHolder.Description
                .setText(list.get(position).description);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listner.click(index);
            }
        });
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