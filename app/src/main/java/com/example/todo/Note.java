package com.example.todo;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    String title;
    String description;
    boolean checkClick;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
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

    public boolean isCheckClick() {
        return checkClick;
    }

    public void setCheckClick(boolean checkClick) {
        this.checkClick = checkClick;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        checkClick = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeByte((byte) (checkClick ? 1 : 0));
    }
}
