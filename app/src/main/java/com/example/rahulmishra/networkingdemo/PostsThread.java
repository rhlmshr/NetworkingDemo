package com.example.rahulmishra.networkingdemo;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rahul Mishra on 08-07-2017.
 */

public class PostsThread extends AsyncTask<String, Void, List<Post_Comments>> {

    OnDownloadCompleteListener mListener ;

    void setOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        mListener = listener;
    }

    @Override
    protected List<Post_Comments> doInBackground(String... params) {
        String urlPosts = params[0] ;

        try {
            URL url = new URL(urlPosts) ;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection() ;
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream() ;

            Scanner scanner = new Scanner(inputStream) ;
            String postText = "" ;
            while(scanner.hasNext())   {
                postText += scanner.nextLine() ;
            }
            return parsePosts(postText) ;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }

    private List<Post_Comments> parsePosts(String postText) {
        try {
            JSONArray array = new JSONArray(postText) ;
            List<Post_Comments> postList = new ArrayList<>() ;

            for(int i = 0; i < array.length(); i++) {
                JSONObject currentJsonObject = (JSONObject) array.get(i);
                int id = currentJsonObject.getInt("id") ;
                String title = currentJsonObject.getString("title") ;
                Post_Comments post_comments = new Post_Comments(id, title) ;
                postList.add(post_comments) ;
            }
            return postList ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null ;
    }

    @Override
    protected void onPostExecute(List<Post_Comments> post_comments) {
        super.onPostExecute(post_comments);
        if(mListener != null)
            mListener.onDownloadComplete(post_comments) ;
    }
}

interface  OnDownloadCompleteListener   {
    void onDownloadComplete(List<Post_Comments> postsList) ;
}
