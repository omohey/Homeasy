package com.example.project2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project2.MainActivity;
import com.example.project2.R;
import com.example.project2.customer_booking;
import com.example.project2.customer_main;
import com.example.project2.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    String customerID;

    View root;
    customer_main activity;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //HomeViewModel homeViewModel =
                //new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        activity = (customer_main) getActivity();
        customerID = activity.getCustomerID();

        int width = activity.getWidth();
        width =width/3;

        ImageView imageView = (ImageView) root.findViewById(R.id.logout);
        imageView.setOnClickListener(this);


        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        ImageView Im = (ImageView) root.findViewById(R.id.carpenterimage);
        Im.setOnClickListener(this);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) Im.getLayoutParams();
        params.width = width;
        Im.setLayoutParams(params);

//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.electrician_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.plumber_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.gardner_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.nanny_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.housekeeping_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.cook_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.painter_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);

        Im = (ImageView) root.findViewById(R.id.ac_image);
        Im.setOnClickListener(this);
        Im.setLayoutParams(params);
//        Im.setMaxWidth(width);




        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.logout)
        {
            Intent i = new Intent(binding.getRoot().getContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        else
        {
            Intent i = new Intent(binding.getRoot().getContext(), customer_booking.class);
            switch (v.getId())
            {
                case R.id.carpenterimage: i.putExtra("Job", "Carpenter");
                    break;
                case R.id.ac_image: i.putExtra("Job", "AC Repair");
                    break;
                case R.id.cook_image: i.putExtra("Job", "Cook");
                    break;
                case R.id.electrician_image: i.putExtra("Job", "Electrician");
                    break;
                case R.id.gardner_image:i.putExtra("Job", "Gardener");
                    break;
                case R.id.housekeeping_image: i.putExtra("Job", "House Keeping");
                    break;
                case R.id.painter_image: i.putExtra("Job", "Painter");
                    break;
                case R.id.nanny_image:i.putExtra("Job", "Nanny");
                    break;
                case R.id.plumber_image: i.putExtra("Job", "Plumber");
                    break;
            }
            i.putExtra("ID", customerID);



            startActivity(i);
        }


    }
    public void onclick2()
    {

    }
}