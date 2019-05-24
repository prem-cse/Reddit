package com.example.reddit.Comments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reddit.Model.Entry.Entry;
import com.example.reddit.Model.Entry.ExtractEntry;
import com.example.reddit.Model.Feed;
import com.example.reddit.R;
import com.example.reddit.feedAPI;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class CommentsActivty extends AppCompatActivity {

    private static final String TAG = "CommentsActivty";
    private static String postUrl;
    private static String postthumb;
    private static String postauthor;
    private static String postdate;
    private static String posttitle;
    private static int defaultImage;
    private static String currfeed;
    private static final String BASE_URL = "https://www.reddit.com/r/";
    private static List<Comment> mComment;
    private static ListView listView;
    private static ProgressBar commentLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        setupImageLoader();
        init();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        feedAPI feed = retrofit.create(feedAPI.class);

        Call<Feed> call = feed.getFeed(currfeed);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Log.d(TAG,"OnResponse : Server Response : "+response.toString());
                List<Entry> entrysList = response.body().getEntry();
                for(int i=0;i<entrysList.size();++i){
                    Log.d(TAG," onResponse entry : "+entrysList.get(i).toString()+
                            "\n---------------------------------------------------------------\n");
                    ExtractEntry entry = new ExtractEntry("<div class=\"md\"><p>",entrysList.get(i).getContent(),"</p>");
                    List<String> commentsDetails = entry.start();

                    try {
                        mComment.add(new Comment(entrysList.get(i).getAuthor().getName(),
                                entrysList.get(i).getId(),
                                entrysList.get(i).getUpdated(),
                                commentsDetails.get(0)));

                    }catch (IndexOutOfBoundsException e){

                        mComment.add(new Comment("None","None", "None", "Error in reading comment"));
                        Log.d(TAG," Exception : "+e.getMessage());
                    }catch(NullPointerException e){

                        mComment.add(new Comment("None",
                                entrysList.get(i).getId(),
                                entrysList.get(i).getUpdated(),
                                commentsDetails.get(0)));
                        Log.d(TAG," Exception : "+e.getMessage());
                    }
                }
                listView.setAdapter(new CommentsListAdapter(CommentsActivty.this,R.layout.comments_layout, (ArrayList<Comment>) mComment));
                commentLoadingProgressBar.setVisibility(View.GONE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        getComments();
                    }
                });



            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {

                Log.e(TAG,"onFailure: "+t.getMessage());
                Toast.makeText(CommentsActivty.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        final Intent incomingIntent = getIntent();
        postUrl = incomingIntent.getStringExtra("@string/post_url");
        postauthor = incomingIntent.getStringExtra("@string/post_author");
        postdate = incomingIntent.getStringExtra("@string/post_date");
        postthumb = incomingIntent.getStringExtra("@string/post_thumb");
        posttitle = incomingIntent.getStringExtra("@string/post_title");
        mComment = new ArrayList<>();
        listView = findViewById(R.id.commentsListView);
        commentLoadingProgressBar = findViewById(R.id.commentLoadingProgressBar);


        TextView title = findViewById(R.id.postTitle);
        TextView author = findViewById(R.id.postAuthor);
        TextView updated = findViewById(R.id.postUpdated);
        ImageView thumb = findViewById(R.id.postThumbnail);
        Button reply = findViewById(R.id.postReply);
        ProgressBar postLoadingProgressBar = findViewById(R.id.postLoadingProgressBar);

        title.setText(posttitle);
        author.setText(postauthor);
        updated.setText(postdate);
        commentLoadingProgressBar.setVisibility(View.VISIBLE);
        setImage(postthumb,thumb,postLoadingProgressBar);

        try{
            String[] spiltUrl = postUrl.split(BASE_URL);
            currfeed = spiltUrl[1];
            Log.d(TAG," Current Feed : "+currfeed);
        }catch (ArrayIndexOutOfBoundsException e){
            Log.d(TAG," Exception : "+e.getMessage());
        }

        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              getComments();
            }
        });

        thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Opening url in webview : "+postUrl);
                Intent intent = new Intent(CommentsActivty.this,WebViewActivity.class);
                intent.putExtra("@string/post_url",postUrl);
                startActivity(intent);
            }
        });


    }

    private void getComments() {
        final Dialog dialog = new Dialog(CommentsActivty.this);
        dialog.setTitle("dialog");
        dialog.setContentView(R.layout.comment_input_dialog);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.30);

        dialog.getWindow().setLayout(width,height);
        dialog.show();
    }

    private void setImage(String thumbUrl, ImageView imageView, final ProgressBar progressBar){
        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();


        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(thumbUrl, imageView, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );


    }

    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                CommentsActivty.this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
         defaultImage = CommentsActivty.this.getResources().getIdentifier("@drawable/placeholder_img",null,CommentsActivty.this.getPackageName());

    }
}
