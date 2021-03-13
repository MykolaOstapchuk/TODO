package com.example.todo.Fragments;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Note;
import com.example.todo.R;
import com.example.todo.ToDoAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivityFragment extends Fragment {

    public interface openAddNoteFragment {
        void openNoteFragment();
    }

    private openAddNoteFragment addNoteFragment;
    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;

    private static List<Note> list = new ArrayList<>();
    private List<Note> insertList;

    private boolean addNewElement=false;
    private String title;
    private String description;
    private Button addNoteBtn;
    private Button deleteAllNoteBtn;


    public MainActivityFragment() { }

    public MainActivityFragment(String title, String description){
        this.title        =title;
        this.description  =description;
        this.addNewElement=true;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addNoteFragment = (openAddNoteFragment) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.noteRecyclerView);
        addNoteBtn = view.findViewById(R.id.addNoteBtn);
        deleteAllNoteBtn = view.findViewById(R.id.deleteAllNoteBtn);

        insertList = new ArrayList<>();
        toDoAdapter = new ToDoAdapter(getContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(toDoAdapter);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        if(addNewElement){
            addNewElement=false;
            insertList.clear();
            insertList.addAll(list);
            insertList.add(new Note(title,description));
            list.clear();
            list.addAll(insertList);
            toDoAdapter.updateData(insertList);
            recyclerView.smoothScrollToPosition(toDoAdapter.getItemCount()-1); //Auto scroll to last item
        }


        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                addNoteFragment.openNoteFragment();
            }
        });

        deleteAllNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                recyclerView.setAdapter(null);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_activity, container, false);
    }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            list.remove(viewHolder.getAdapterPosition());
            toDoAdapter.notifyDataSetChanged();

//            if(list.size()==0){
//                recyclerView.setAdapter(null);
//            }else {
//                toDoAdapter.updateData(list);
//            }
        }
    };
}