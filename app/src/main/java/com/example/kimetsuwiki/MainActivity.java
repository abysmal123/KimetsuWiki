//package com.example.kimetsuwiki;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.os.Looper;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    private TextView t1;  //用于显示获取的信息
//    private Button sendmsg;  //按钮
//    private EditText et_msg;//用户输入的信息
//    private String user="用户名";
//    private boolean T=false;//发送标志位
//    //数据库连接类
//    private static Connection con = null;
//    private static PreparedStatement stmt = null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        t1 = findViewById(R.id.t1);
//        et_msg = findViewById(R.id.msg);
//        sendmsg = findViewById(R.id.button);
//        sendmsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { T=true; }
//        });
//
//        Threads_sendmsg threads_sendmsg = new Threads_sendmsg();
//        threads_sendmsg.start();
//
//        Threads_readSQL threads_readSQL = new Threads_readSQL();
//        threads_readSQL.start();
//    }
//
//    class Threads_sendmsg extends Thread {
//        @Override
//        public void run() {
//            while (true){
//                while (T){
//                    try {
//                        con = MySQLConnections.getConnection();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        String msg =et_msg.getText().toString().trim();    //用户发送的信息
//
//                        if (msg.isEmpty()){
//
//                            Looper.prepare();
//                            Toast.makeText(MainActivity.this, "请不要发送空消息", Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                            T=false;
//                            break;
//                        }
//                        if (msg.length()>199){
//                            Looper.prepare();
//                            Toast.makeText(MainActivity.this, "请不要发送200字符以上的信息", Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                            T=false;
//                            break;
//                        }
//
//                        if (con!=null) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            String sql = "insert into test(name,msg)  values(?,?)";
//                            stmt = con.prepareStatement(sql);
//                            // 关闭事务自动提交 ,这一行必须加上
//                            con.setAutoCommit(false);
//                            stmt.setString(1,user);
//                            stmt.setString(2,msg);
//                            stmt.addBatch();
//                            stmt.executeBatch();
//                            con.commit();
//                        }
//                    }catch (SQLException e){
//                        System.out.println(e);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                    T=false;
//                }
//            }
//        }
//    }
//    class Threads_readSQL extends Thread {
//        @Override
//        public void run() {
//            while (true){
//                try {
//                    con = MySQLConnections.getConnection();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    String sql ="Select distinct name,msg from test order by id";
//                    if (con!=null) {
//
//                        stmt = con.prepareStatement(sql);
//                        // 关闭事务自动提交 ,这一行必须加上
//                        con.setAutoCommit(false);
//                        ResultSet rs = stmt.executeQuery();//创建数据对象
//                        //清空上次发送的信息
//                        t1.setText("");
//                        while (rs.next()) {
//                            t1.append(rs.getString(1) + "\n" + rs.getString(2)+ "\n\n");
//                        }
//                        con.commit();
//                        rs.close();
//                        stmt.close();
//
//                    }
//                    //2秒更新一次
//                    sleep(2000);
//                }catch (Exception e){
//                    System.out.println(e);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Toast.makeText(MainActivity.this,"请输入正确的语句",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        }
//    }
//}
package com.example.kimetsuwiki;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Character> goodCharacterList;
    private ArrayList<Character> badCharacterList;
    private ArrayList<Comment> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.goodCharacterList = new ArrayList<>();
        this.badCharacterList = new ArrayList<>();
        this.commentList = new ArrayList<>();


        Threads_readSQL thRead = new Threads_readSQL();
        thRead.start();
        initData();

        TextView kimetsuInfo = findViewById(R.id.kimetsuInfo);
        kimetsuInfo.setText(R.string.kimetsu_info);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        try {
            thRead.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (this.goodCharacterList, this.badCharacterList, this.commentList, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new
                TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {
           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {
           }


       });
    }

    private void initData() {
        String[] goodCharacterNames = getResources()
                .getStringArray(R.array.good_character_name);
        String[] goodCharacterInfos = getResources()
                .getStringArray(R.array.good_character_info);
        String[] goodCharacterRelation = getResources()
                .getStringArray(R.array.good_character_relation);
        String[] goodCharacterFight = getResources()
                .getStringArray(R.array.good_character_fight);
        TypedArray goodCharacterImageResources =
                getResources().obtainTypedArray(R.array.good_character_images);
        this.goodCharacterList.clear();
        for(int i = 0; i < goodCharacterNames.length; i++){
            this.goodCharacterList.add(new Character(i, goodCharacterNames[i],
                    goodCharacterInfos[i], goodCharacterRelation[i],
                    goodCharacterFight[i], goodCharacterImageResources.getResourceId(i,0)));
        }
        goodCharacterImageResources.recycle();

        String[] badCharacterNames = getResources()
                .getStringArray(R.array.bad_character_name);
        String[] badCharacterInfos = getResources()
                .getStringArray(R.array.bad_character_info);
        String[] badCharacterRelation = getResources()
                .getStringArray(R.array.bad_character_relation);
        String[] badCharacterFight = getResources()
                .getStringArray(R.array.bad_character_fight);
        TypedArray badCharacterImageResources =
                getResources().obtainTypedArray(R.array.bad_character_images);
        this.badCharacterList.clear();
        for(int i = 0; i < badCharacterNames.length; i++){
            this.badCharacterList.add(new Character(i, badCharacterNames[i],
                    badCharacterInfos[i], badCharacterRelation[i],
                    badCharacterFight[i], badCharacterImageResources.getResourceId(i,0)));
        }
        badCharacterImageResources.recycle();

//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        String date = year + "/" + month + "/" + day;
//        for(int i = 0; i < 5; i++) {
//            this.commentList.add(new Comment("这是留言" + i, date));
//        }
    }

    class Threads_readSQL extends Thread {
        @Override
        public void run() {
            commentList = getCommentData();
        }

        public ArrayList<Comment> getCommentData(){
            //结果存放集合
            ArrayList<Comment> list=new ArrayList<Comment>();
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            //MySQL 语句
            String sql="select * from comments order by date desc";
            //获取链接数据库对象
            conn= MySQLConnections.getConnection();
            try {
                if(conn != null && (!conn.isClosed())){
                    stmt = conn.prepareStatement(sql);
                    if(stmt!=null){
                        rs= stmt.executeQuery();
                        if(rs!=null){
                            while(rs.next()){
                                Comment c=new Comment();
                                c.setText(rs.getString("text"));
                                c.setDate(rs.getString("date"));
                                list.add(c);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            MySQLConnections.closeAll(conn,stmt,rs);//关闭相关操作
            return list;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String reply = data.getStringExtra("reply");
        if(reply.equals("ok")) {
            Threads_readSQL thRead = new Threads_readSQL();
            thRead.start();

            TabLayout tabLayout = findViewById(R.id.tab_layout);
            final ViewPager viewPager = findViewById(R.id.pager);

            try {
                thRead.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final PagerAdapter adapter = new PagerAdapter
                    (this.goodCharacterList, this.badCharacterList, this.commentList, getSupportFragmentManager(), tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
        }
    }

}