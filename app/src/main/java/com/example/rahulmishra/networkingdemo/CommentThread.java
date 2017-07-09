package com.example.rahulmishra.networkingdemo;

import android.os.AsyncTask;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rahul Mishra on 08-07-2017.
 */

public class CommentThread extends AsyncTask<String, Void, Post_Comments> {

    OnDownloadCompleteListener mListener ;

    void setOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        mListener = listener ;
    }

    @Override
    protected Post_Comments doInBackground(String... params) {
        String urlComments = params[0] ;
        int positionInList = Integer.parseInt(params[1]) ;
        Post_Comments currentPost = MainActivity.postContent.get(positionInList) ;

        try {
            URL url = new URL(urlComments) ;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection() ;
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream() ;

            Scanner scanner = new Scanner(inputStream) ;
            String commentsText = "" ;
            while(scanner.hasNext())   {
                commentsText += scanner.nextLine() ;
            }
            return parseComments(currentPost, commentsText) ;

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null ;
    }

    private Post_Comments parseComments(Post_Comments currentPost, String commentsText) {
        try {
            JSONArray array = new JSONArray(commentsText) ;
            Pair<ArrayList<String>, ArrayList<String>> commentsList = currentPost.getComments() ;
            for(int i = 0; i < array.length(); i++) {
                JSONObject currentJsonObject = (JSONObject) array.get(i);
                String comment = currentJsonObject.getString("body") ;
                String name = currentJsonObject.getString("email") ;

                commentsList.first.get(i) = name ;
                commentsList.second.get(i) = comment ;
            }
            return currentPost ;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null ;
    }

    @Override
    protected void onPostExecute(Post_Comments post_comments) {
        super.onPostExecute(post_comments);
        if(mListener != null)
            mListener.onDownloadComplete(post_comments.getComments().second);
    }
}

//interface // TODO: 09-07-2017  