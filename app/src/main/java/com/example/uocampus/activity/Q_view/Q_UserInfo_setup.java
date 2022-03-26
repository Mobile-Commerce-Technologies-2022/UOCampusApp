package com.example.uocampus.activity.Q_view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uocampus.R;

public class Q_UserInfo_setup extends AppCompatDialogFragment {
    private static final String TAG = "";
    private EditText name,phone,sid;
    private DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_user_info, null);
        Log.d(TAG,"Enter dialog");
        builder.setView(view)
                .setTitle("User Info")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            String nameTXT = name.getText().toString();
                            String phoneTXT = phone.getText().toString();
                            String studentTXT = sid.getText().toString();
                            listener.applyTexts(nameTXT,phoneTXT,studentTXT);
                    }
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
            throw new ClassCastException(context.toString()+"must implement DialogueLisenter");
        }
    }

    public interface DialogListener{
            void applyTexts(String name,String phone, String sid);
        }
}