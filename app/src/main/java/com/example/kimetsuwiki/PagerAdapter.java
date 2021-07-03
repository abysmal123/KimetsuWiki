package com.example.kimetsuwiki;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private ArrayList<Character> goodCharacterList;
    private ArrayList<Character> badCharacterList;
    private ArrayList<Comment> commentList;

    public PagerAdapter(ArrayList<Character> goodCharacterList, ArrayList<Character> badCharacterList, ArrayList<Comment> commentList, FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.goodCharacterList = goodCharacterList;
        this.badCharacterList = badCharacterList;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TabFragment1(this.goodCharacterList);
            case 1: return new TabFragment2(this.badCharacterList);
            case 2: return new TabFragment3(this.commentList);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
