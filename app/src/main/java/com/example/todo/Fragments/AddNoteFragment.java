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

import static com.example.todo.Constants.DESCRIPTION_ERROR;
import static com.example.todo.Constants.LONG_TITLE_ERROR;
import static com.example.todo.Constants.TITLE_ERROR;

public class AddNoteFragment extends Fragment {

    private noteFragment noteFragment;
    private EditText title, description;
    boolean checkEdit = false;
    private String a, b = "";
    private String oldTitle, oldDescription;
    private int pos;

    public interface noteFragment {
        void addNote(boolean check, String title, String description);

        void editNote(String title, String description, int position);
    }

    public AddNoteFragment() {
    }

    public AddNoteFragment(String a, String b, int pos) {
        this.a = a;
        this.b = b;
        this.pos = pos;
        checkEdit = true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            noteFragment = (noteFragment) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        title = view.findViewById(R.id.titleTxt);
        description = view.findViewById(R.id.descriptionTxt);
        Button confirm = view.findViewById(R.id.confirmBtn);
        Button cancel = view.findViewById(R.id.cancelBtn);

        if (checkEdit) {
            oldTitle = title.getText().toString();
            oldDescription = description.getText().toString();

            title.setText(a);
            description.setText(b);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tit = title.getText().toString();
                String des = description.getText().toString();

                tit = tit.replace("\n", "").replace("\r", "");
                des = des.replace("\n", "").replace("\r", "");

                if (TextUtils.isEmpty(tit)) {
                    title.setError(TITLE_ERROR);
                } else if (TextUtils.isEmpty(des)) {
                    description.setError(DESCRIPTION_ERROR);
                } else {
                    if (tit.length() >= 20) {
                        title.setError(LONG_TITLE_ERROR);
                    } else {
                        if (!checkEdit)
                            noteFragment.addNote(true, tit, des);
                        else
                            noteFragment.editNote(tit, des, pos);
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkEdit)
                    noteFragment.addNote(false, "", "");
                else
                    noteFragment.editNote(oldTitle, oldDescription, -1);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}