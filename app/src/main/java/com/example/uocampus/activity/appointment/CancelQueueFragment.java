package com.example.uocampus.activity.appointment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.uocampus.R;

public class CancelQueueFragment extends AppCompatDialogFragment {
    private EditText sid;
    private CancelQueueFragment.DialogListener listener;

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_cancelqueue, null);
        builder.setView(view)
                .setTitle("Cancel User Info")
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("Confirm", (dialogInterface, i) -> {
                    String studentNum = sid.getText().toString();
                    listener.cancelAppointment(studentNum);
                });
        sid = view.findViewById(R.id.cancelsid);
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

    public interface DialogListener {
        void cancelAppointment(String studentTXT);
    }
}