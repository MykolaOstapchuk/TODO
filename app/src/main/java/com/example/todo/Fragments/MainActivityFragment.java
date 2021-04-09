package com.example.todo.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Note;
import com.example.todo.R;
import com.example.todo.RecyclerView.SimpleDividerItemDecoration;
import com.example.todo.ToDoAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment implements ToDoAdapter.OnNoteListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onNoteClick(int position,boolean choose) {
        if(choose){
        addNoteFragment.openNoteFragment(true, list.get(position).getTitle(),list.get(position).getDescription(),position);
        Log.d("kola","onNoteClick: clicked. " +String.valueOf(position));
        }
        else
            deleteElement(position);
    }

    public interface openAddNoteFragment {
        void openNoteFragment(boolean check , String title, String description, int position);
    }

    private void deleteElement(int pos){
        list.remove(pos);

        toDoAdapter.notifyItemChanged(pos);
        toDoAdapter.notifyItemRangeRemoved(pos, 1);
    }

    private openAddNoteFragment addNoteFragment;
    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;
    private boolean isNightModeOn=false;

    private static List<Note> list = new ArrayList<>();

    private boolean addNewElement = false;
    private String title;
    private String description;
    private int position;
    private boolean editNote =false;

    private Button darkMode;

    public MainActivityFragment() {
        this.addNewElement = false;
    }

    public MainActivityFragment(String title, String description) {
        this.title = title;
        this.description = description;
        this.addNewElement = true;
    }

    public MainActivityFragment(String title, String description,int pos) {
        this.title = title;
        this.description = description;
        this.position = pos;
        this.editNote = true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferences = context.getSharedPreferences("AppSettingPrefs",0);
        editor = sharedPreferences.edit();
        editor.apply();
        isNightModeOn = sharedPreferences.getBoolean("NightMode",false);
        try {
            addNoteFragment = (openAddNoteFragment) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        Button addNoteBtn = view.findViewById(R.id.add_note_button);

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        toDoAdapter = new ToDoAdapter(getContext(), list,this);
        recyclerView.setAdapter(toDoAdapter);

        if (addNewElement) {
            addNewElement = false;
            list.add(new Note(title, description));
            recyclerView.scrollToPosition(list.size() - 1);
        }
        else if(editNote) {
            editNote = false;
            list.get(position).setTitle(title);
            list.get(position).setDescription(description);
        }

        if (list.size() != 0) {
            toDoAdapter.submitList(list);
        }

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                addNoteFragment.openNoteFragment(false,"","",0);
            }
        });


        darkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("NightMode",false);
                    editor.commit();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("NightMode",true);
                    editor.commit();
                }
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);

        Button deleteAllNoteBtn = rootView.findViewById(R.id.delete_notes_button);
        Button options = rootView.findViewById(R.id.option_button);
        darkMode         = rootView.findViewById(R.id.dark_mode_button);

        deleteAllNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                recyclerView.setAdapter(null);
            }
        });

        recyclerView = rootView.findViewById(R.id.noteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
        recyclerView.setAdapter(null);

        return rootView;
    }
}