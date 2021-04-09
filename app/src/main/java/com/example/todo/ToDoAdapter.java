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

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private static AsyncListDiffer<Note> mDiffer;
    private Context context;
    private OnNoteListener monNoteListener;
    private List<Note> a;

    //
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public final DiffUtil.ItemCallback<Note> DIFF_UTIL = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return TextUtils.equals(oldItem.title, newItem.title);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.title.equals(newItem.title) && oldItem.description.equals(newItem.description);
        }
    };

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public ToDoAdapter(Context ct, List<Note> ls, OnNoteListener onNoteListener) {
        context = ct;
        this.monNoteListener = onNoteListener;
        a = ls;
        mDiffer = new AsyncListDiffer<Note>(this, DIFF_UTIL);
    }

    public void submitList(List<Note> data) {
        mDiffer.submitList(data);
    }

    public Note getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_todo, parent, false);
        return new MyViewHolder(view,monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(a.get(holder.getAdapterPosition()).title));
        viewBinderHelper.closeLayout(String.valueOf(a.get(holder.getAdapterPosition()).title));

        //holder.bindData(getItem(position));
                holder.tempcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = holder.getAdapterPosition();
                if (b) {
                    mDiffer.getCurrentList().get(pos).setCheckClick(true);
                    holder.tekst1.setPaintFlags(holder.tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mDiffer.getCurrentList().get(pos).setCheckClick(false);
                    holder.tekst1.setPaintFlags(holder.tekst1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
        holder.setData(getItem(position));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tekst1;
        private TextView tekst2;
        private CheckBox tempcheck;
        private OnNoteListener onNoteListener;

        //
        private  TextView edit,delete,t;
        private SwipeRevealLayout swipeRevealLayout;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            tekst1 = itemView.findViewById(R.id.noteTitle);
            tekst2 = itemView.findViewById(R.id.noteDescription);
            tempcheck = itemView.findViewById(R.id.checkbox);
            this.onNoteListener = onNoteListener;

            //
            edit = itemView.findViewById(R.id.editTextBtn);
            delete= itemView.findViewById(R.id.deleteTextBtn);
            //t = itemView.findViewById(R.id.textView);
            swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monNoteListener.onNoteClick(getAdapterPosition(),true);
                    Toast.makeText(context, "Edit is Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monNoteListener.onNoteClick(getAdapterPosition(),false);
                    Toast.makeText(context, "Delete is Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(this);
        }

        void bindData(Note note){
            t.setText(note.getTitle());
        }

        public void setData(Note note) {
            boolean help = note.isCheckClick();

            tekst1.setText(note.getTitle());
            if (help) {
                tekst1.setPaintFlags(tekst1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                tekst1.setPaintFlags(tekst1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            String desc = note.getDescription();
            if (desc.length() >= 40) {
                String upToNCharacters = desc.substring(0, 40);
                tekst2.setText(upToNCharacters + " ...");
            } else
                tekst2.setText(note.getDescription());

            tempcheck.setChecked(note.isCheckClick());
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition(),true);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position,boolean choose);
    }
}


