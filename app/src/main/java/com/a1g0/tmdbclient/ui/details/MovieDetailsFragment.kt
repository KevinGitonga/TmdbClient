package com.a1g0.tmdbclient.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import coil.load
import com.a1g0.tmdbclient.R
import com.faltenreich.skeletonlayout.Skeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import com.a1g0.tmdbclient.data.model.Cast
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.data.model.MovieDB
import com.a1g0.tmdbclient.data.model.Status
import com.a1g0.tmdbclient.databinding.FragmentMovieDetailsBinding
import com.a1g0.tmdbclient.ui.dialog.VideoPlayerDialog
import com.a1g0.tmdbclient.ui.adapter.CastRecyclerViewAdapter
import com.a1g0.tmdbclient.ui.adapter.SimilarMoviesRecyclerViewAdapter
import com.a1g0.tmdbclient.utils.Constants
import com.a1g0.tmdbclient.utils.NetworkUtils
import com.a1g0.tmdbclient.utils.showToast
import com.a1g0.tmdbclient.utils.toHours
import com.faltenreich.skeletonlayout.applySkeleton

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var movie : MovieDB

    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding : FragmentMovieDetailsBinding

    private var castList : ArrayList<Cast> = ArrayList()
    private var similarList : ArrayList<Movie> = ArrayList()

    private lateinit var castRecyclerViewAdapter : CastRecyclerViewAdapter
    private lateinit var similarRecyclerViewAdapter : SimilarMoviesRecyclerViewAdapter

    private lateinit var castSkeleton: Skeleton
    private lateinit var similarMovieSkeleton: Skeleton

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)
        binding = FragmentMovieDetailsBinding.bind(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movie = requireArguments().get(Constants.movie) as MovieDB

        viewModel.movieName.value = movie.title
        //viewModel.movie.value = movie
        viewModel.getVideos(movie.id)

        binding.buttonBack.setOnClickListener(this)
        binding.fabPlayButton.setOnClickListener(this)

        initAdapters()

        if(NetworkUtils.isOnline(requireContext())){
            loadData()

            loadCast()

            loadSimilar()

            viewModel.getMovieDetails(movie.id)
        }
        else{
            showToast(getString(R.string.check_network_to_continue),Toast.LENGTH_LONG)
        }


    }

    private fun initAdapters() {
        similarRecyclerViewAdapter = SimilarMoviesRecyclerViewAdapter(requireContext(), similarList)
        castRecyclerViewAdapter = CastRecyclerViewAdapter(requireContext(), castList)

        binding.recyclerViewCast.adapter = castRecyclerViewAdapter
        binding.recyclerViewRelated.adapter = similarRecyclerViewAdapter

        castSkeleton = binding.recyclerViewCast.applySkeleton(R.layout.item_cast, 10)
        similarMovieSkeleton = binding.recyclerViewRelated.applySkeleton(R.layout.item_similar_movie, 10)
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {

        viewModel.movie.observe(requireActivity(), Observer {

            var genre: String = ""
            if (!it.genres.isNullOrEmpty())
                for (i in 0..it.genres.size - 1) {
                    genre += Constants.getGenreMap()[it.genres[i].id].toString()
                    if (i != it.genres.size - 1) {
                        genre += "• "
                    }
                }

            binding.apply {
                textMovieName.text = it!!.title
                textRating.text = "${it.vote_average}/10"
                textReleaseDate.text = it.release_date
                textDescription.text = it.overview
                if (it.runtime != null)
                    textLength.text = toHours(it.runtime!!)
                textGenres.text = genre

                detailsBannerImage.load(Constants.IMAGE_BASE_URL + it.backdrop_path) {
                    placeholder(Constants.viewPagerPlaceHolder.random())
                    error(Constants.viewPagerPlaceHolder.random())
                }

                imagePoster.load(Constants.IMAGE_BASE_URL + it.poster_path) {
                    placeholder(Constants.moviePlaceHolder.random())
                    error(Constants.moviePlaceHolder.random())
                }
            }

        })

    }

    private fun loadSimilar() {
        viewModel.loadSimilar(movie.id).observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    if (similarList.isNotEmpty())
                        similarMovieSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    similarList.clear()
                    similarList.addAll(it.data!!.results)
                    similarRecyclerViewAdapter.notifyDataSetChanged()
                    similarMovieSkeleton.showOriginal()

                    if (similarList.isNullOrEmpty()) {
                        binding.headingRelated.visibility = View.GONE
                    } else {
                        binding.headingRelated.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }

    private fun loadCast() {
        viewModel.loadCast(movie.id).observe(requireActivity(), Observer {
            when (it.status) {
                Status.LOADING -> {
                    if (castList.isNullOrEmpty())
                        castSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    castSkeleton.showOriginal()
                    castList.clear()
                    castList.addAll(it.data!!.cast)
                    castRecyclerViewAdapter.notifyDataSetChanged()

                    if (castList.isNullOrEmpty()) {
                        binding.headingCast.visibility = View.GONE
                    } else {
                        binding.headingCast.visibility = View.VISIBLE
                    }

                }
                Status.ERROR -> {
                    showToast("Something went wrong!")
                }
            }
        })
    }



    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.fabPlayButton -> {
                if (viewModel.videos.value != null && viewModel.videos.value!!.results.size != 0) {
                    val videoDialog = VideoPlayerDialog(viewModel.videos.value!!.results[0].key)
                    videoDialog.show(childFragmentManager, "Video Dialog")
                }
                else {
                    showToast("Video not found!")
                }
            }
            R.id.button_back -> {
                binding.root.findNavController().navigateUp()
            }

        }

    }

}