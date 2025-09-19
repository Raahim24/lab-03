package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    private City city;
    private EditCityDialogListener listener;

    // Listener for communicating back to MainActivity
    interface EditCityDialogListener {
        void onCityEdited();
    }

    // Factory method (recommended)
    public static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city); // City must implement Serializable
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Load the city object
        city = (City) requireArguments().getSerializable("city");

        // Pre-fill the fields
        editCityName.setText(city.getName());
        editProvinceName.setText(city.getProvince());

        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Update the City object
                    city.setName(editCityName.getText().toString());
                    city.setProvince(editProvinceName.getText().toString());
                    listener.onCityEdited(); // notify activity to refresh
                })
                .create();
    }
}

