package com.example.todo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.todo.Fragments.AddNoteFragment;
import com.example.todo.Fragments.MainActivityFragment;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.openAddNoteFragment, AddNoteFragment.noteFragment {

    private Fragment cuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            cuFragment = new MainActivityFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_page, cuFragment)
                    .addToBackStack("MainPageFragment")
                    .commit();

        }
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, "startFragment", cuFragment);
//    }

//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        cuFragment = getSupportFragmentManager().getFragment(savedInstanceState, "startFragment");
//    }

    @Override
    public void openNoteFragment(boolean check, String title, String description,int position) {
        getSupportFragmentManager().popBackStack();

        if (!check) {
            cuFragment = new AddNoteFragment();
        } else {
            cuFragment = new AddNoteFragment(title, description,position);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_page, cuFragment)
                .addToBackStack("AddNoteFragment")
                .commit();
    }



    @Override
    public void addNote(boolean check, String title, String description) {
        getSupportFragmentManager().popBackStack();

        if (!check) {
            cuFragment = new MainActivityFragment();
        } else {
            cuFragment = new MainActivityFragment(title, description);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_page, cuFragment)
                .addToBackStack("MainActivityFragment")
                .commit();
    }

    @Override
    public void editNote(String title, String description,int pos) {
        getSupportFragmentManager().popBackStack();

        cuFragment = new MainActivityFragment(title, description,pos);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_page, cuFragment)
                .addToBackStack("MainActivityFragment")
                .commit();
    }
}