package com.example.rahulmishra.networkingdemo;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Rahul Mishra on 08-07-2017.
 */

public class Post_Comments {

    private int id ;
    private String post ;
    private Pair<ArrayList<String>, ArrayList<String>> comments ;

    public Post_Comments(int id, String post) {
        this.id = id;
        this.post = post;
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        this.comments = new Pair<>(first,second);
     }

    public int getId() {
        return id;
    }

    public String getPost() {
        return post;
    }

    public Pair<ArrayList<String>, ArrayList<String>> getComments() {
        return comments ;
    }

    public void setComments(Pair<ArrayList<String>, ArrayList<String>> comments) {
        this.comments = comments;
    }
}
