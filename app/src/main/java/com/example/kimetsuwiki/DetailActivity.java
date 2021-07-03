package com.example.kimetsuwiki;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView name = findViewById(R.id.name);
        ImageView avatar = findViewById(R.id.characterImage);
        TextView info = findViewById(R.id.info_content);
        TextView relation = findViewById(R.id.relation_content);
        TextView fight = findViewById(R.id.fight_content);

        name.setText(getIntent().getStringExtra("name"));
        info.setText(getIntent().getStringExtra("info"));
        relation.setText(getIntent().getStringExtra("relation"));
        fight.setText(getIntent().getStringExtra("fight"));

        Glide.with(this).load(getIntent().getIntExtra("avatar",0))
                .into(avatar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}