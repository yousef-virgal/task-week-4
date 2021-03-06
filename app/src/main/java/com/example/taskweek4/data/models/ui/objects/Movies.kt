package com.example.taskweek4.data.models.ui.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movies(
    @PrimaryKey
    val movieId:Int?,
    val voteAverage:Double?,
    val title:String?,
    val releaseDate:String?,
    val backdropPath:String?,
    val posterPath:String?,
    val overview:String?,
    val mediaType:String?,
    val voteCount:Double?,
    val name:String?,
    var fav:Boolean = false

)