package com.example.kimetsuwiki;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<Comment> commentData;
    private Context mContext;

    CommentAdapter(Context context, ArrayList<Comment> commentData) {
        this.commentData = commentData;
        this.mContext = context;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.comment_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment currentSport = commentData.get(position);
        holder.bindTo(currentSport);
    }

    @Override
    public int getItemCount() {
        return this.commentData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mCommentText;
        private TextView mCommentDate;


        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mCommentText = itemView.findViewById(R.id.comment_text);
            mCommentDate = itemView.findViewById(R.id.comment_date);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Comment currentCharacter){
            // Populate the textviews with data.
            mCommentText.setText(currentCharacter.getText());
            mCommentDate.setText(currentCharacter.getDate().substring(0, 16));

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View view) {
            // no action
        }
    }
}
