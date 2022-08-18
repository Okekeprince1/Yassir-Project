package com.example.yassirproject.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yassirproject.databinding.MovieItemBinding
import com.example.yassirproject.domain.model.home.Movie
import com.example.yassirproject.utils.getYearFromDate
import com.example.yassirproject.utils.load

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.MovieViewHolder>() {

    private val items = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMovies(movies: List<Movie>) {
        items.clear()
        items.addAll(movies)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMovies(movies: List<Movie>){
        items.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class MovieViewHolder(
        private val binding: MovieItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                Log.d("ClickedPost", items[adapterPosition].toString())
                items[adapterPosition].onClick.invoke()
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Movie) {
            binding.apply {
                movieTitle.text = item.title
                movieYear.text = item.year.getYearFromDate()
                movieImage.load(item.imageUrl)
                movieRating.text = "${item.vote_average}/${ item.vote_count }"
            }
        }
    }
}