package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;



public class ImageGalleryAdapter2 extends RecyclerView.Adapter<AppointmentViewHolder> {

    List<AppointListData> list
            = Collections.emptyList();

    Context context;
    ClickListner listner;

    public ImageGalleryAdapter2(List<AppointListData> list,
                                Context context, ClickListner listiner)
    {
        this.list = list;
        this.context = context;
        this.listner = listiner;
    }

    @Override
    public AppointmentViewHolder
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
                .inflate(R.layout.oneitem,
                        parent, false);

        AppointmentViewHolder viewHolder
                = new AppointmentViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final AppointmentViewHolder viewHolder,
                     final int position)
    {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.WorkerName
                .setText(list.get(position).name);
        viewHolder.Date
                .setText(list.get(position).date);
        viewHolder.Time
                .setText(list.get(position).time);
        viewHolder.Jobtype.setText(list.get(position).Job);
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