package com.example.reddit;

import com.example.reddit.Model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface feedAPI  {
    String BASE_URL = "https://www.reddit.com/r/";

    @GET("{feed_name}/.rss")
    Call<Feed> getFeed(@Path("feed_name") String feed_name);

//    static way
//    @GET("earthporn/.rss")
//    Call<Feed> getFeed();
}

