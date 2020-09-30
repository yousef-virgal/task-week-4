package com.example.taskweek4.recyclerview

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieRepo
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var  pref: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor
    private val nonNormalViewHolder = 0
    private val normalViewHolder =1
    private val loadingViewHolder =2
    private val blankViewHolder =3
    private var myPosition:Int= 0
    var tempCheck:Boolean = false
   //  var favoriteViewModel: FavoriteViewModel =  ViewModelProvider().get(
      //  FavoriteViewModel::class.java)


    class NormalMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val moviePoster:ImageView = itemView.findViewById(R.id.movie_poster)
        val movieName:TextView = itemView.findViewById(R.id.movie_name)
        val movieScore:TextView = itemView.findViewById(R.id.movie_genre)
        val movieType:TextView = itemView.findViewById(R.id.movie_duration)
        val favButton:ToggleButton = itemView.findViewById(R.id.favToggleButton)

    }

    class NonNormalMovieHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val moviePoster1:ImageView = itemView.findViewById(R.id.firstPoster)
        val moviePoster2:ImageView = itemView.findViewById(R.id.secondPoster)
        val moviePoster3:ImageView = itemView.findViewById(R.id.thirdPoster)
    }

    class BlankViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            nonNormalViewHolder -> {
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_2, parent, false)
                return NonNormalMovieHolder(v)
            }
            normalViewHolder -> {

                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return NormalMovieHolder(v)
            }
            blankViewHolder->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.blank, parent, false)
                return BlankViewHolder(v)

            }
            loadingViewHolder->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
                return LoadingViewHolder(v)
            }

            else->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return NormalMovieHolder(v)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalMovieHolder ) {
            holder.itemView.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken", MODE_PRIVATE)
                edt = pref.edit()
                clickListener(position,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_homefragment_to_itemFragment)
            }




            holder.favButton.setOnCheckedChangeListener { _, favChecked ->

                println("favChecked = $favChecked")

                /*if(movies[position].fav == true)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)
                if(movies[position].fav == false)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)*/


                movies[position].fav = favChecked

                if(movies[position].fav == true)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)
                if(movies[position].fav == false)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)


               // Toast.makeText(holder.itemView.context, if( favChecked) " ${holder.movieName.text}  is added to Favorites" else " ${holder.movieName.text} is removed from Favorites", Toast.LENGTH_SHORT).show()
                MovieRepo.changeMovie(movies[position])



               //favoriteViewModel.changeMovieFromViewModel(MovieRepo,movies[position])

              //  holder.favButton.isChecked = !holder.favButton.isChecked
            }

            if(movies[position].fav == true)
            {
                holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)
                holder.favButton.isChecked = true
            }
            if(movies[position].fav == false) {
                holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)
            }











            if (movies[position].mediaType == "movie")
                holder.movieName.text = movies[position].title
            else
                holder.movieName.text = movies[position].name
            holder.movieScore.text = movies[position].voteAverage.toString()
            holder.movieType.text = movies[position].mediaType
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position].posterPath)
                .into(holder.moviePoster)
        }

        else if(holder is NonNormalMovieHolder){
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position].posterPath)
                .into(holder.moviePoster1)
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position+1].posterPath)
                .into(holder.moviePoster2)
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position+2].posterPath)
                .into(holder.moviePoster3)

            holder.moviePoster1.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken", MODE_PRIVATE)
                edt = pref.edit()
                clickListener(position,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_homefragment_to_itemFragment)

            }
            holder.moviePoster2.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken", MODE_PRIVATE)
                edt = pref.edit()
                clickListener(position+1,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_homefragment_to_itemFragment)
            }
            holder.moviePoster3.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken", MODE_PRIVATE)
                edt = pref.edit()
                clickListener(position+2,edt,movies)
                holder.itemView.findNavController()
                    .navigate(R.id.action_homefragment_to_itemFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    fun addData(list: List<Movies>){
        movies.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        movies.clear()
    }




    override fun getItemViewType(position: Int): Int {

        return when (position) {
            0 -> {
                myPosition = position
                nonNormalViewHolder
            }
            1,2->{
                blankViewHolder
            }

            movies.size - 1 -> {
                loadingViewHolder
            }
            else -> {
                myPosition = position
                normalViewHolder
            }
        }

    }
}

fun clickListener(position:Int,edt: SharedPreferences.Editor,movies:List<Movies>){


    edt.putString("posterPath", movies[position].posterPath)
    edt.putString("backdropPath", movies[position].backdropPath)
    edt.putString("name", movies[position].name)
    edt.putLong("voteCount",movies[position].voteCount!!.toLong())
    edt.putString("title", movies[position].title)
    edt.putString("overView", movies[position].overview)
    edt.putFloat("rate", movies[position].voteAverage!!.toFloat())
    edt.putString("releaseDate", movies[position].releaseDate)
    edt.putInt("movieId", movies[position].movieId!!)
    edt.putBoolean("isFavored",movies[position].fav)
    edt.putString("mediaType",movies[position].mediaType)


    edt.apply()


}
