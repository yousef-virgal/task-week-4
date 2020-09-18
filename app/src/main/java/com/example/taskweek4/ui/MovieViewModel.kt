package com.example.taskweek4.ui
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieCallBack
import com.example.taskweek4.repository.MovieRepo
import android.app.Application

class MovieViewModel(application: Application) : AndroidViewModel(application),MovieCallBack
{

    private val _movieLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    val movieLiveData: LiveData<List<Movies>>
        get() = _movieLiveData


    private lateinit var movieData: List<Movies>
    private var currentSpinner = "movie"
    private var currentPage= 1



    init {
        MovieRepo.createDatabase(application)
    }



    fun loadMovieData(spinnerData:String ,page:Int){
        if (spinnerData == currentSpinner && this::movieData.isInitialized&&page==currentPage) {
            _movieLiveData.value = movieData
            return
        }
        currentSpinner = spinnerData
        currentPage = page
        MovieRepo.getData(this,currentSpinner,page)
    }


    override fun isReady(movies: List<Movies>) {
        movieData = movies
        _movieLiveData.value =movieData
    }

    fun isLoading():Boolean{
        return MovieRepo.isLoading()
    }

}