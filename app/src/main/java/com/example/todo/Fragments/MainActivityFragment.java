package com.example.todo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Note;
import com.example.todo.R;
import com.example.todo.RecyclerView.SimpleDividerItemDecoration;
import com.example.todo.ToDoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private Button deleteAllNoteBtn;

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

        Button addNoteBtn = view.findViewById(R.id.addNoteBtn);


        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //btn.setText("Disable Dark Mode");
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //btn.setText("Enable Dark Mode");
        }

//        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("AppSettingPrefs",0);
//        // sharedPreferences= Objects.requireNonNull(getContext()).getSharedPreferences("AppSettingPrefs",0);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//         isNightModeOn = sharedPreferences.getBoolean("NightMode",false);

        toDoAdapter = new ToDoAdapter(getContext(), list,this);
        recyclerView.setAdapter(toDoAdapter);

        //new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);


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




        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);

        deleteAllNoteBtn = rootView.findViewById(R.id.deleteAllNoteBtn);
        deleteAllNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("NightMode",false);
                    editor.commit();

                    //btn.setText("Enable Dark Mode");
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("NightMode",true);
                    editor.commit();
                    //btn.setText("Disable Dark Mode");
                }

                //list.clear();
                //recyclerView.setAdapter(null);
            }
        });

        recyclerView = rootView.findViewById(R.id.noteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));

        recyclerView.setAdapter(null);

        return rootView;
    }

//    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            list.remove(viewHolder.getAdapterPosition());
//
//            toDoAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
//            toDoAdapter.notifyItemRangeRemoved(viewHolder.getAdapterPosition(), 1);
//        }
//    };
}