package com.example.ILoveEat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class Food_detailActivity extends AppCompatActivity implements CommentslistFragment.OnFragmentInteractionListener {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mRecyclerView;//定义RecyclerView
    private ArrayList<Comment> commentList = new ArrayList<Comment>();

    private RecycleAdapter_comments mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment();

            commentList.add(comment);
        }
    }

    private void initRecyclerView() {
        //获取RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_comments);
        //创建adapter
        mRecyclerAdapter = new RecycleAdapter_comments(getBaseContext(), commentList);
        //给RecyclerView设置adapter
        mRecyclerView.setAdapter(mRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mRecyclerAdapter.setOnItemClickListener(new RecycleAdapter_comments.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Comment data) {


                //Toast.makeText(this,"我是item",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Startwritecomment(View view) {
        Intent intent = new Intent(this, WritecommentActivity.class);
        startActivity(intent);
    }

}
