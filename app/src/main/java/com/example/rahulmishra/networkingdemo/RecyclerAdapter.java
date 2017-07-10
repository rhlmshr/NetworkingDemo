package com.example.rahulmishra.networkingdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Mishra on 10-07-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Pair<ArrayList<String>, ArrayList<String>> comments ;
    private Context mContext ;

    public RecyclerAdapter(Context context, Post_Comments post_comments) {
        this.comments = post_comments.getComments() ;
        this.mContext = context ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView commentBox, nameBox ;
        protected RecyclerView view ;

        public ViewHolder(View itemView) {
            super(itemView);
            commentBox = (TextView) itemView.findViewById(R.id.commentBox) ;
            nameBox = (TextView) itemView.findViewById(R.id.nameBox) ;
            view = (RecyclerView) itemView.findViewById(R.id.listCard) ;
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.single_item, parent, false) ;
        ViewHolder mHolder = new ViewHolder(v) ;
        return mHolder ;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        ArrayList<String> name = this.comments.first ;
        ArrayList<String> comments = this.comments.second ;

        String nameText = name.get(position);
        String commentText = comments.get(position);

        Log.d("TAG", nameText + ": " + commentText);


        holder.nameBox.setText(nameText);
        holder.commentBox.setText(commentText);
    }

    @Override
    public int getItemCount() {
        return comments.first.size() ;
    }
}
