package com.example.kimetsuwiki;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    TextView content_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button submit = findViewById(R.id.submit);
        content_display = findViewById(R.id.content_display);

        submit.setOnClickListener(v -> {
            String content = content_display.getText().toString().trim();
            if(content.length() == 0) {
                Toast.makeText(getApplicationContext(), "请不要以空消息作为留言！",
                        Toast.LENGTH_SHORT).show();
            } else {
                submit.setClickable(false);
                Threads_sendSQL thSend = new Threads_sendSQL();
                thSend.start();
                try {
                    thSend.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "留言成功！",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("reply", "ok");
                setResult(0, intent);
                this.finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra("reply", "no");
            setResult(0, intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("reply", "no");
        this.setResult(0, intent);
        this.finish();
    }


    class Threads_sendSQL extends Thread {
        @Override
        public void run() {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //MySQL 语句
            String sql="insert into comments(text, date) values(?, ?)";
            //获取链接数据库对象
            conn= MySQLConnections.getConnection();
            try {
                if(conn != null && (!conn.isClosed())){
                    stmt = conn.prepareStatement(sql);
                    conn.setAutoCommit(false);
                    String text = content_display.getText().toString().trim();
                    SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date d = new Date(System.currentTimeMillis());
                    String date = date_format.format(d);
                    stmt.setString(1, text);
                    stmt.setString(2, date);
                    stmt.addBatch();
                    stmt.executeBatch();
                    conn.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            MySQLConnections.closeAll(conn,stmt,rs);//关闭相关操作
        }
    }
}