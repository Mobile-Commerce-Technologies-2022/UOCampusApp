package com.example.uocampus.activity.appointment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.uocampus.R;

public class UserInfoFragment extends AppCompatDialogFragment {
    private static final String TAG = "";
    private EditText name,phone,sid;
    private DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_user_info, null);
        Log.d(TAG,"Enter dialog");
        builder.setView(view)
                .setTitle("User Info")
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("Confirm", (dialogInterface, i) -> {
                        String nameTXT = name.getText().toString();
                        String phoneTXT = phone.getText().toString();
                        String studentTXT = sid.getText().toString();
                        listener.addAppointment(nameTXT,phoneTXT,studentTXT);
                });
        name = view.findViewById(R.id.Name);
        phone = view.findViewById(R.id.Phone);
        sid = view.findViewById(R.id.idNumber);

        return builder.create();
        }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context +"must implement DialogueListener");
        }
    }

    public interface DialogListener{
            void addAppointment(String name, String phone, String sid);
    }
}