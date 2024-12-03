package com.example.tiffincart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.tiffincart.Adapter.AllConsultantAdapter;
import com.example.tiffincart.Adapter.ConsultantAdapter;
import com.example.tiffincart.Model.ConsultantItem;
import com.example.tiffincart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllConsultantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllConsultantFragment extends Fragment {

    private ImageButton backButton;
    private RecyclerView allConsultantRecyclerView;

    public AllConsultantFragment() {
        // Required empty public constructor
    }


    public static AllConsultantFragment newInstance(String param1, String param2) {
        AllConsultantFragment fragment = new AllConsultantFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_consultant, container, false);
        ImageButton backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack(); // Pop the current fragment and go back
        });

        allConsultantRecyclerView = view.findViewById(R.id.allConsultantsRecyclerView);
        allConsultantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        List<ConsultantItem> consultantItems = new ArrayList<>();
        consultantItems.add(new ConsultantItem("Consultant 1", R.drawable.user2, "Lorem ipsum dolor sit amet..."));
        consultantItems.add(new ConsultantItem("Consultant 2", R.drawable.user2, "Lorem ipsum dolor sit amet..."));
        consultantItems.add(new ConsultantItem("Consultant 3", R.drawable.user1, "Lorem ipsum dolor sit amet..."));
        consultantItems.add(new ConsultantItem("Consultant 4", R.drawable.user1, "Lorem ipsum dolor sit amet..."));
        consultantItems.add(new ConsultantItem("Consultant 5", R.drawable.user2, "Lorem ipsum dolor sit amet..."));
        consultantItems.add(new ConsultantItem("Consultant 6", R.drawable.user2, "Lorem ipsum dolor sit amet..."));

        AllConsultantAdapter allConsultantAdapter = new AllConsultantAdapter(consultantItems);
        allConsultantRecyclerView.setAdapter(allConsultantAdapter);

        return  view;

    }
}