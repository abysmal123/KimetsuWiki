package com.example.kimetsuwiki;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment3 extends Fragment {

    private View view;
    public RecyclerView mRecyclerView;
    public CommentAdapter mAdapter;
    private ArrayList<Comment> commentList;

    public TabFragment3(ArrayList<Comment> commentList) {
        // Required empty public constructor
        this.commentList = commentList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view =  inflater.inflate(R.layout.tab_fragment3, container, false);
        initRecyclerView();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CommentActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        return this.view;
    }

    private void initRecyclerView() {
        this.mRecyclerView = (RecyclerView)this.view.findViewById(R.id.comment_wall);
        this.mAdapter = new CommentAdapter(getActivity(), this.commentList);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}