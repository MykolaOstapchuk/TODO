package com.example.todo;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Util.MyDiffUtillCallback;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {


//    public final DiffUtil.ItemCallback<Note> DIFF_UTIL = new DiffUtil.ItemCallback<Note>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
//            return  TextUtils.equals(oldItem.title,newItem.title);
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
//            return oldItem.title.equals(newItem.title);
//        }
//    };

    //private AsyncListDiffer<Note> mDiffer;

    //@Override
    //public int getItemCount(){
    //    return mDiffer.getCurrentList().size();
    //}


    @Override
    public int getItemCount() {
        return workList.size();
    }

    List<Note> workList = new ArrayList<>();
    Context context;

    public ToDoAdapter(Context ct, List<Note> ls){
        context = ct;
        //mDiffer = new AsyncListDiffer<Note>(this,DIFF_UTIL);
        workList = ls;
    }

    public void setWorkList(List<Note> newList){
        this.workList=newList;
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
        MyDiffUtillCallback diffUtilCallback = new MyDiffUtillCallback(workList,newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);

        //Toast.makeText(context,"WorkListSize= "+workList.size()+ " /n NewListSize= "+newList.size(),Toast.LENGTH_LONG).show();

        workList.clear();
        workList.addAll(newList);


        //mDiffer.submitList(workList);

        //Toast.makeText(context,"WorkListSize= "+workList.size(),Toast.LENGTH_LONG).show();
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_todo,parent,false);



        return new MyViewHolder(view);
    }


    //public void submitList(List<Note> data) {
    //    mDiffer.submitList(data);
    //}


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Note note = mDiffer.getCurrentList().get(position);

        String temp = workList.get(position).title;
        boolean help= workList.get(position).isCheckClick();

        holder.tekst1.setText(temp);
        holder.tempcheck.setChecked(help);


        if(help){
            holder.tekst1.setPaintFlags(holder.tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else
            holder.tekst1.setPaintFlags( holder.tekst1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));


        holder.tempcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.tekst1.setPaintFlags(holder.tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.tekst1.setPaintFlags( holder.tekst1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tekst1;
        CheckBox tempcheck;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tekst1   = itemView.findViewById(R.id.noteTitle);
            tempcheck= itemView.findViewById(R.id.checkbox);
        }
    }
}


