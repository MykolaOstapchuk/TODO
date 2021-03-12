package com.example.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Util.MyDiffUtillCallback;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    List<Note> workList;
    Context context;
    CheckBox checkBox;

    public ToDoAdapter(Context ct, List<Note> ls){
        context = ct;
        workList = ls;
    }

    public void insertData(List<Note> insertList){
        //This function will add new data to RecyclerView
        MyDiffUtillCallback diffUtillCallback = new MyDiffUtillCallback(workList,insertList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtillCallback);

        workList.addAll(insertList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void updateData(List<Note> newList){
        //This function will update data to RecyclerView
        MyDiffUtillCallback diffUtillCallback = new MyDiffUtillCallback(workList,newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtillCallback);

        workList.clear();
        workList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_todo,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String temp = workList.get(position).title;
        boolean help= workList.get(position).isCheckClick();

        holder.tekst1.setText(temp);
        holder.tempcheck.setChecked(help);

        if(help){
            holder.tekst1.setPaintFlags(holder.tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(workList.get(position).isCheckClick())
                {
                    workList.get(position).setCheckClick(false);
                    holder.tekst1.setPaintFlags( holder.tekst1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
                else {
                    workList.get(position).setCheckClick(true);
                    holder.tekst1.setPaintFlags(holder.tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return workList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tekst1;
        CheckBox tempcheck;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tekst1   = itemView.findViewById(R.id.noteTitle);
            checkBox = itemView.findViewById(R.id.checkbox);
            tempcheck= itemView.findViewById(R.id.checkbox);
        }
    }
}


