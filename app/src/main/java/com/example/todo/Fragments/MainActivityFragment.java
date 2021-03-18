package com.example.todo.Fragments;

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
import com.example.todo.RecyclerView.SimpleDividerItemDecoration;
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

    private boolean addNewElement=false;
    private String title;
    private String description;
    private Button addNoteBtn;
    private Button deleteAllNoteBtn;

    public MainActivityFragment() {this.addNewElement=false;}

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

        addNoteBtn = view.findViewById(R.id.addNoteBtn);
        deleteAllNoteBtn = view.findViewById(R.id.deleteAllNoteBtn);

        toDoAdapter = new ToDoAdapter(getContext(), list);


        recyclerView.setAdapter(toDoAdapter);

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        if(addNewElement){
            addNewElement=false;
            list.add(new Note(title,description));
            recyclerView.scrollToPosition(list.size()-1); //Auto scroll to last item
        }

        if(list.size()!=0){
            toDoAdapter.submitList(list);
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
        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);

        recyclerView = rootView.findViewById(R.id.noteRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));

        recyclerView.setAdapter(null);
        return rootView;
    }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            list.remove(viewHolder.getAdapterPosition());

            toDoAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
            toDoAdapter.notifyItemRangeRemoved(viewHolder.getAdapterPosition(),1);
        }
    };
}