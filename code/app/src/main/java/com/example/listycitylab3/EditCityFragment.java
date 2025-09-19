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

    // Listener interface to communicate back to MainActivity
    interface EditCityDialogListener {
        void editCity(int position, String newName, String newProvince);
    }

    private EditCityDialogListener listener;

    private int position;   // position of city in list
    private String name;    // current name
    private String province; // current province

    // Setter method for passing data
    public void setCityData(int position, String name, String province) {
        this.position = position;
        this.name = name;
        this.province = province;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill current values
        editCityName.setText(name);
        editProvinceName.setText(province);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = editCityName.getText().toString();
                    String newProvince = editProvinceName.getText().toString();
                    listener.editCity(position, newName, newProvince);
                })
                .create();
    }
}
