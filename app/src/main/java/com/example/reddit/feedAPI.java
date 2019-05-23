package com.example.reddit;

import com.example.reddit.Model.Feed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface feedAPI  {
    String BASE_URL = "https://www.reddit.com/r/";

    @GET("earthporn/.rss")
    Call<Feed> getFeed();
}

