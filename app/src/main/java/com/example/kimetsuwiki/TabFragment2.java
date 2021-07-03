package com.example.kimetsuwiki;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment2 extends Fragment {

    private View view;
    public RecyclerView mRecyclerView;
    public CharacterAdapter mAdapter;
    private ArrayList<Character> characterList;

    public TabFragment2(ArrayList<Character> characterList) {
        // Required empty public constructor
        this.characterList = characterList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view =  inflater.inflate(R.layout.tab_fragment2, container, false);
        initRecyclerView();
        return this.view;
    }

    private void initRecyclerView() {
        this.mRecyclerView = (RecyclerView)this.view.findViewById(R.id.bad_characters);
        this.mAdapter = new CharacterAdapter(getActivity(), this.characterList);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}