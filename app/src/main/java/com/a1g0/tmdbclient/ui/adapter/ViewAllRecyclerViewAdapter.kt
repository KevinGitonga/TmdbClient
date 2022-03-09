package com.a1g0.tmdbclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.a1g0.tmdbclient.R
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.data.model.MovieDB
import com.a1g0.tmdbclient.databinding.ItemSearchBinding
import com.a1g0.tmdbclient.utils.Constants

@Suppress("DEPRECATION")
class ViewAllRecyclerViewAdapter :
    PagingDataAdapter<Movie, ViewAllRecyclerViewAdapter.ViewHolder>(MOVIE_COMPARATOR) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemSearchBinding.bind(itemView)

        fun bind(movie: Movie) {
            binding.apply {

                searchImage.load(Constants.IMAGE_BASE_URL + movie.poster_path) {
                    placeholder(Constants.moviePlaceHolder[position])
                    error(Constants.moviePlaceHolder[position])
                }

                itemView.setOnClickListener {
                    val bundle = bundleOf(Constants.movie to MovieDB(movie.id, movie.poster_path.toString(),
                    movie.overview.toString(), movie.title.toString(), movie.backdrop_path.toString(), "",
                        movie.vote_average!!
                    ))
                    it.findNavController().navigate(R.id.action_viewAllFragment_to_movieDetailsFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
