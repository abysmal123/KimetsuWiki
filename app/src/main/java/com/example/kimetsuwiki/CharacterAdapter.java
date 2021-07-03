package com.example.kimetsuwiki;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private ArrayList<Character> characterData;
    private Context mContext;
    private static String trans = "slide";
    CharacterAdapter(Context context, ArrayList<Character> characterData) {
        this.characterData = characterData;
        this.mContext = context;
    }

    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.character_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        Character currentSport = characterData.get(position);
        holder.bindTo(currentSport);
    }

    @Override
    public int getItemCount() {
        return characterData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mCharacterName;
        private ImageView mCharacterImage;


        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mCharacterName = itemView.findViewById(R.id.name);
            mCharacterImage = itemView.findViewById(R.id.characterImage);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Character currentCharacter){
            // Populate the textviews with data.
            mCharacterName.setText(currentCharacter.getName());
            Glide.with(mContext).load(currentCharacter.getAvatar()).into(mCharacterImage);


        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            Character currentSport = characterData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("name", currentSport.getName());
            detailIntent.putExtra("info", currentSport.getShortInfo());
            detailIntent.putExtra("relation", currentSport.getRelation());
            detailIntent.putExtra("fight", currentSport.getFightExp());
            detailIntent.putExtra("avatar", currentSport.getAvatar());
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity)mContext, (View)mCharacterImage, trans);
            mContext.startActivity(detailIntent, options.toBundle());
        }
    }
}
