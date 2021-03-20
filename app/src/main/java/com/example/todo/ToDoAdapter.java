package com.example.todo;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private static AsyncListDiffer<Note> mDiffer;
    private List<Note> workList;
    private Context context;

    public final DiffUtil.ItemCallback<Note> DIFF_UTIL = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return  TextUtils.equals(oldItem.title,newItem.title);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.title.equals(newItem.title) && oldItem.description.equals(newItem.description);
        }
    };

    @Override
    public int getItemCount(){
        return mDiffer.getCurrentList().size();
    }

    public ToDoAdapter(Context ct, List<Note> ls){
        context = ct;
        mDiffer = new AsyncListDiffer<Note>(this,DIFF_UTIL);
        workList = mDiffer.getCurrentList();
    }

    public void submitList(List<Note> data) {
        //mDiffer.submitList(new ArrayList<>());
        mDiffer.submitList(data);
    }

    public Note getItem(int position) {
        return mDiffer.getCurrentList().get(position);
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
        holder.tempcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = holder.getAdapterPosition();
                Toast.makeText(context,"Position of item =" +position+"     pos="+pos,Toast.LENGTH_LONG).show();
                if(b){
                    mDiffer.getCurrentList().get(pos).setCheckClick(true);
                    holder.tekst1.setPaintFlags(holder.tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    mDiffer.getCurrentList().get(pos).setCheckClick(false);
                    holder.tekst1.setPaintFlags( holder.tekst1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

//        holder.tempcheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if(mDiffer.getCurrentList().get(pos).isCheckClick() == false)
//                    mDiffer.getCurrentList().get(pos).setCheckClick(true);
//
//            }
//        });
        holder.setData(getItem(position));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tekst1;
        private TextView tekst2;
        private CheckBox tempcheck;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tekst1   = itemView.findViewById(R.id.noteTitle);
            tekst2   = itemView.findViewById(R.id.noteDescription);
            tempcheck= itemView.findViewById(R.id.checkbox);
        }

        public void setData(Note note){
            boolean help = note.isCheckClick();

            tekst1.setText(note.getTitle());
            if(help){
                //tempcheck.setChecked(true);
                tekst1.setPaintFlags(tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }else{
                //tempcheck.setChecked(false);
                tekst1.setPaintFlags(tekst1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            String desc  = note.getDescription();
            if(desc.length() >= 40){
                String upToNCharacters = desc.substring(0, 40);
                tekst2.setText(upToNCharacters+" ...");
            }else
                tekst2.setText(note.getDescription());


            tempcheck.setChecked(note.isCheckClick());
        }
    }
}


