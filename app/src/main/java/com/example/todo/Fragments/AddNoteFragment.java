package com.example.todo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todo.R;


public class AddNoteFragment extends Fragment {

    public interface noteFragment {
        void addNote(boolean check, String title, String decription);
    }

    private noteFragment noteFragment;
    private EditText title,description;
    private Button   confirm,cancel;

    public AddNoteFragment() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            noteFragment = (noteFragment) context;
        }catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title       = view.findViewById(R.id.titleTxt);
        description = view.findViewById(R.id.descriptionTxt);
        confirm     = view.findViewById(R.id.confirmBtn);
        cancel      = view.findViewById(R.id.cancelBtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tit = title.getText().toString();
                String des = description.getText().toString();

                tit = tit.replace("\n", "").replace("\r", "");
                des = des.replace("\n", "").replace("\r", "");

                if(TextUtils.isEmpty(tit)) {
                    title.setError("The item title cannot be empty");
                    return;
                }
                else if(TextUtils.isEmpty(des)) {
                    description.setError("The item description cannot be empty");
                    return;
                }
                else {
                    if(tit.length() >= 20){
                        title.setError("The title name should have less than 20 characters");
                    }
                    else
                    noteFragment.addNote(true,tit,des);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteFragment.addNote(false,"","");
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}