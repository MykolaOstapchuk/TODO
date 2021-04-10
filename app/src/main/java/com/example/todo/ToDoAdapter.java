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
    private List<Note> currentList;
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
        currentList = ls;
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
        return new MyViewHolder(view, monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(currentList.get(holder.getAdapterPosition()).title));
        viewBinderHelper.closeLayout(String.valueOf(currentList.get(holder.getAdapterPosition()).title));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = holder.getAdapterPosition();
                if (b) {
                    mDiffer.getCurrentList().get(pos).setCheckClick(true);
                    holder.title_text.setPaintFlags(holder.title_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    mDiffer.getCurrentList().get(pos).setCheckClick(false);
                    holder.title_text.setPaintFlags(holder.title_text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
        holder.setData(getItem(position));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title_text;
        private TextView description_text;
        private CheckBox checkBox;
        private OnNoteListener onNoteListener;
        private SwipeRevealLayout swipeRevealLayout;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            title_text = itemView.findViewById(R.id.noteTitle);
            description_text = itemView.findViewById(R.id.noteDescription);
            checkBox = itemView.findViewById(R.id.checkbox);
            this.onNoteListener = onNoteListener;

            TextView edit = itemView.findViewById(R.id.editTextBtn);
            TextView delete = itemView.findViewById(R.id.deleteTextBtn);
            swipeRevealLayout = itemView.findViewById(R.id.swipeLayout);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monNoteListener.onNoteClick(getAdapterPosition(), true);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monNoteListener.onNoteClick(getAdapterPosition(), false);
                }
            });

            itemView.setOnClickListener(this);
        }

        public void setData(Note note) {
            boolean help = note.isCheckClick();

            title_text.setText(note.getTitle());
            if (help) {
                title_text.setPaintFlags(title_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                title_text.setPaintFlags(title_text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            String desc = note.getDescription();
            if (desc.length() >= 40) {
                String upToNCharacters = desc.substring(0, 40);
                description_text.setText(upToNCharacters + " ...");
            } else
                description_text.setText(note.getDescription());

            checkBox.setChecked(note.isCheckClick());
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition(), true);
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position, boolean choose);
    }
}


