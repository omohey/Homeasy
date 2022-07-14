package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class GalleryAdapterWorkerApps extends RecyclerView.Adapter<AppointmentViewHolderWorkerApps> {

    List<AppointListWorkerApps> list
            = Collections.emptyList();

    Context context;
    ClickListner listner;

    public GalleryAdapterWorkerApps(List<AppointListWorkerApps> list,
                                Context context, ClickListner listiner)
    {
        this.list = list;
        this.context = context;
        this.listner = listiner;
    }

    @Override
    public AppointmentViewHolderWorkerApps
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
                .inflate(R.layout.oneitem_workerapps,
                        parent, false);

        AppointmentViewHolderWorkerApps viewHolder
                = new AppointmentViewHolderWorkerApps(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final AppointmentViewHolderWorkerApps viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.WorkerName
                .setText(list.get(position).name);
        viewHolder.Date
                .setText(list.get(position).date);
        viewHolder.Time
                .setText(list.get(position).time);
        viewHolder.Location.setText(list.get(position).Address);
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