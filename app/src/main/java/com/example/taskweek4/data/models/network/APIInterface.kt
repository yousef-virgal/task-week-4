package com.example.taskweek4.data.models.network

import com.example.taskweek4.data.models.remote.MovieResponse
import com.example.taskweek4.data.models.remote.ReviewResponse
import com.example.taskweek4.data.models.remote.VideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface{
    @GET("trending/{media_type}/{time_window}")
    fun getPopularMovies(
        @Path("media_type") mediaType:String,
        @Path("time_window") timeWindow:String,
        @Query("api_key") apiKey:String,
        @Query("page") page:Int
    ): Call<MovieResponse>

    @GET("search/multi")
    fun searchForMovies(
        @Query("api_key") apiKey:String,
        @Query ("query") query:String,
        @Query("page") page:Int
    ):Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(
        @Query("api_key") apiKey:String,
        @Query("page") page:Int
    ):Call<MovieResponse>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(
        @Query("api_key") apiKey:String,
        @Path("movie_id") movieId:Int,
        @Path("page") page:Int
    ):Callback<ReviewResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideos(
        @Query("api_key") apiKey:String,
        @Path("movie_id") movieId:String
    ):Callback<VideoResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendations(
        @Query("api_key") apiKey:String,
        @Query("page") page:Int,
        @Path("movie_id") movieId:Int
    ):Callback<MovieResponse>
}

object ApiClient{
    private var retrofit: Retrofit? =null
    fun getRetrofit():Retrofit?{
        if(retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp3.OkHttpClient.Builder().build())
                .build()
        return retrofit
    }
}