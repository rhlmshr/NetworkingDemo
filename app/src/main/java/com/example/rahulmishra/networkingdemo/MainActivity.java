package com.example.rahulmishra.networkingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDownloadCompleteListener {

    public static List<Post_Comments> postContent ;
    ArrayAdapter<String> adapter ;
    List<String> posts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        ListView postList = (ListView) findViewById(R.id.postList) ;
        postContent = new ArrayList<>() ;
        posts = new ArrayList<>() ;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, posts) ;
        postList.setAdapter(adapter);
        fetchPosts() ;

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, Comments_List.class) ;
                i.putExtra("position", position);
                startActivity(i) ;
            }
        });
    }

    private void fetchPosts() {
        String postsURL = "http://jsonplaceholder.typicode.com/posts" ;
        PostsThread postsThread = new PostsThread() ;
        postsThread.execute(postsURL) ;
        postsThread.setOnDownloadCompleteListener(this);
    }

    public void onDownloadComplete(List<Post_Comments> postList){
        postContent.clear();
        postContent.addAll(postList);
        for(int i = 0; i < postList.size(); i++){
            posts.add(postList.get(i).getPost());
        }
        adapter.notifyDataSetChanged();
    }
}
