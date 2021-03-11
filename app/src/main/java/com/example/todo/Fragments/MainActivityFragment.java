package com.example.todo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Note;
import com.example.todo.R;
import com.example.todo.ToDoAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment {


    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;
    private Button addNoteBtn;

    private List<Note> list;

    public MainActivityFragment() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.noteRecyclerView);
        addNoteBtn   = view.findViewById(R.id.addNoteBtn);

        toDoAdapter = new ToDoAdapter(getContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(toDoAdapter);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Note> insertList = new ArrayList<Note>();
                insertList.addAll(list);
                insertList.add(new Note("test","test"));
                toDoAdapter.updateData(insertList);
                recyclerView.smoothScrollToPosition(toDoAdapter.getItemCount()-1); //Auto scroll to last item
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<Note>() ;
        list.add(new Note("kola","work"));
        list.add(new Note("dima","does not work"));
        list.add(new Note("kiril","work hard"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity, container, false);
    }
}