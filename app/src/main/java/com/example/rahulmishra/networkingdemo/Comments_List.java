package com.example.rahulmishra.networkingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Comments_List extends AppCompatActivity implements OnCommentUpdateComplete {

    RecyclerAdapter adapter ;

    Pair<ArrayList<String>, ArrayList<String>> comments ;
    List <Post_Comments> postContent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        postContent = MainActivity.postContent ;

        Intent i = getIntent() ;
        int position = i.getIntExtra("position", 0) ;

        fetchComments(position) ;
        comments = postContent.get(position).getComments() ;

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(this, postContent.get(position)) ;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void fetchComments(int positionInList) {
        Post_Comments currentPost = postContent.get(positionInList) ;

        String commentsURL[] = {"http://jsonplaceholder.typicode.com/comments?postId=" + currentPost.getId(),
                                    Integer.toString(positionInList)};
        CommentThread commentThread = new CommentThread() ;
        commentThread.execute(commentsURL) ;
        commentThread.setOnCommentUpdateListener(this);
    }

    @Override
    public void setOnCommentUpdateListener(Post_Comments post_comments) {
        post_comments.getComments().first.clear();
        post_comments.getComments().second.clear();
        comments.first.addAll(post_comments.getComments().first);
        comments.second.addAll(post_comments.getComments().second);

        adapter.notifyDataSetChanged();
    }
}