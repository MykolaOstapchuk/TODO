package com.example.todo;

public class Note {
    String title;
    String description;
    boolean checkClick;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.checkClick  = false;
    }

    public boolean isCheckClick() {
        return checkClick;
    }

    public void setCheckClick(boolean checkClick) {
        this.checkClick = checkClick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
