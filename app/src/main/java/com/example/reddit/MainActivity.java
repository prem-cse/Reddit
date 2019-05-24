package com.example.reddit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reddit.Comments.CommentsActivty;
import com.example.reddit.Model.Entry.Entry;
import com.example.reddit.Model.Entry.ExtractEntry;
import com.example.reddit.Model.Feed;
import com.example.reddit.Model.Post;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "https://www.reddit.com/r/";
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
//    private List<Post> posts ;
    private EditText searchEdit;
    private Button searchBtn;
    private String currfeed;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        recyclerView=findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        posts = new ArrayList<>();
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBtn = findViewById(R.id.search_button);
        searchEdit = findViewById(R.id.search);
        progressDialog = new ProgressDialog(this);
        init();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedName = searchEdit.getText().toString();
                if(feedName != null){
                    progressDialog.show();
                    progressDialog.setMessage("Searching...");
                    currfeed = feedName;
                    init();
                }else init();
            }
        });

    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        feedAPI feed = retrofit.create(feedAPI.class);

        Call<Feed> call = feed.getFeed(currfeed);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {


//                if (response.body() != null) {
//                    Log.d(TAG,"onSuccess: "+response.body().toString());
//                }

                assert response.body() != null;
                List<Entry> entryList = response.body().getEntry();
                final List<Post> posts = new ArrayList<>();
                for(int i=0;i<entryList.size();++i) {
                    ExtractEntry extractEntry1 = new ExtractEntry("<a href=\"", entryList.get(i).getContent());
                    List<String> contents = extractEntry1.start();

                    ExtractEntry extractEntry2 = new ExtractEntry("<img src=\"", entryList.get(i).getContent());
                    try {
                        contents.add(extractEntry2.start().get(1));
                    } catch (NullPointerException e) {
                        contents.add(null);
                        Log.d(TAG, "nullpointer " + e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        contents.add(null);
                        Log.d(TAG, "index " + e.getMessage());
                    }

                    try {
                        posts.add(new Post(
                                entryList.get(i).getTitle(),
                                entryList.get(i).getAuthor().getName(),
                                entryList.get(i).getUpdated(),
                                contents.get(0),
                                contents.get(contents.size() - 1)));
                    }catch (NullPointerException e){
                        // if author doesn't have name
                        posts.add(new Post(
                                entryList.get(i).getTitle(),
                                "User",
                                entryList.get(i).getUpdated(),
                                contents.get(0),
                                contents.get(contents.size() - 1)));
                        Log.d(TAG,"Null pointer exception : "+e.getMessage());
                    }
                    }


                //    recyclerView.setAdapter(new Adapter(posts,MainActivity.this));


//               for(int i=0;i<posts.size();++i)
//                Log.d(TAG,"onResponse \n"+
//                        "Author : "+posts.get(i).getAuthor()+"\n "+
//                        "Title : "+posts.get(i).getTitle()+" \n "+
//                        "PostUrl : "+posts.get(i).getPostURL()+" \n "+
//                        "ThumbUrl : "+posts.get(i).getThumbnailURL()+" \n ");
//

                progressDialog.dismiss();
                ListView listView = findViewById(R.id.listview);
                listView.setAdapter(new CustomListAdapter(MainActivity.this,R.layout.cardview, (ArrayList<Post>) posts));

             listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                     Log.d(TAG,"On Item Clicked : "+posts.get(i).toString());
                     Intent intent = new Intent(MainActivity.this, CommentsActivty.class);
                     intent.putExtra("@string/post_url",posts.get(i).getPostURL());
                     intent.putExtra("@string/post_thumb",posts.get(i).getThumbnailURL());
                     intent.putExtra("@string/post_author",posts.get(i).getAuthor());
                     intent.putExtra("@string/post_title",posts.get(i).getTitle());
                     intent.putExtra("@string/post_date",posts.get(i).getDate_updated());
                     startActivity(intent);

                 }
             });

            }



            @Override
            public void onFailure(Call<Feed> call, Throwable t) {

                Log.e(TAG,"onFailure: "+t.getMessage());
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });




    }
}
