package com.example.reddit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.reddit.Model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Post> list;
    private Context context;
    public Adapter(List<Post> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        final Post obj=list.get(position);
        holder.cardTitle.setText("Title : "+obj.getTitle());
        holder.author.setText("Author : "+obj.getAuthor());
        holder.updated.setText("Updated : "+obj.getDate_updated());
        Picasso.with(context).load(Uri.parse(String.valueOf(obj.getThumbnailURL()))).into(holder.image);
      //  Glide.with(context).load(Uri.parse(obj.getThumbnailURL())).into(holder.image);
        holder.cardTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(obj.getPostURL())));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView cardTitle;
        public TextView author;
        public TextView updated;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            cardTitle=itemView.findViewById(R.id.cardTitle);
            author =itemView.findViewById(R.id.author);
            updated = itemView.findViewById(R.id.updated);
            image = itemView.findViewById(R.id.cardImage);
        }
    }
}


