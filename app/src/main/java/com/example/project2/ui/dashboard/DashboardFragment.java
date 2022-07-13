package com.example.project2.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project2.R;
import com.example.project2.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView lv = (ListView) root.findViewById(R.id.pendingapps);
        String[] values= new String[] {"Appointment 1\nDate 1", "Appointment 2\nDate 2", "Appointment 3\nDate 3", "Appointment 4\nDate4",
                "Appointment 5\nDate 5", "Appointment 6\nDate 6", "Appointment 7\nDate 7",
                "Appointment 8\nDate 8", "Appointment 9\nDate 9", "Appointment 10\nDate 10", "Appointment 11\nDate11",
                "Appointment 12\nDate 12", "Appointment 13\nDate 13"};
        ArrayAdapter arrayAdapter= new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1,values);
        lv.setAdapter(arrayAdapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}