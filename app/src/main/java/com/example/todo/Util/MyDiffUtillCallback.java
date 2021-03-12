package com.example.todo.Util;

import androidx.recyclerview.widget.DiffUtil;

import com.example.todo.Note;

import java.util.List;

public class MyDiffUtillCallback extends DiffUtil.Callback {

    private List<Note> oldList;
    private List<Note> newList;

    public MyDiffUtillCallback(List<Note> oldList, List<Note> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle());
    }
}
