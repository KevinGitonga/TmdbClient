package com.a1g0.tmdbclient.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.a1g0.tmdbclient.R
import com.faltenreich.skeletonlayout.Skeleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.data.model.MovieDB
import com.a1g0.tmdbclient.data.model.Status
import com.a1g0.tmdbclient.databinding.FragmentHomeBinding
import com.a1g0.tmdbclient.ui.adapter.HomeRecyclerViewAdapter
import com.a1g0.tmdbclient.ui.adapter.HomeViewPagerAdapter
import com.a1g0.tmdbclient.utils.Constants
import com.a1g0.tmdbclient.utils.showToast
import com.faltenreich.skeletonlayout.applySkeleton
import java.lang.Math.abs

@AndroidEntryPoint
class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var navController: NavController

    private val viewModel : HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private var upcomingMovieList : ArrayList<MovieDB> = ArrayList()
    private var popularMovieList : ArrayList<MovieDB> = ArrayList()
    private var topRatedMovieList : ArrayList<MovieDB> = ArrayList()
    private var nowPlayingMovies:ArrayList<MovieDB> = ArrayList()

    private lateinit var upcomingAdapter : HomeRecyclerViewAdapter
    private lateinit var popularAdapter : HomeRecyclerViewAdapter
    private lateinit var topRatedAdapter : HomeRecyclerViewAdapter
    private lateinit var nowPlayingAdapter : HomeRecyclerViewAdapter

    private lateinit var nowPlayingSkeleton : Skeleton
    private lateinit var upcomingSkeleton : Skeleton
    private lateinit var topRatedSkeleton : Skeleton
    private lateinit var popularSkeleton : Skeleton

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navController = Navigation.findNavController(binding.root)

        binding.textViewAllPopular.setOnClickListener(this)
        binding.textViewAllTopRated.setOnClickListener(this)
        binding.textViewAllUpcoming.setOnClickListener(this)
        binding.textViewAllNowPlaying.setOnClickListener(this)

        initAdapters()

        initSkeletons()

        fetchData()

    }

    private fun fetchData() {

        viewModel.loadNowPlayingMovies().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (nowPlayingMovies.isNullOrEmpty())
                        nowPlayingSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    nowPlayingSkeleton.showOriginal()
                    nowPlayingMovies.clear()
                    nowPlayingMovies.addAll(res.data!!)
                    nowPlayingAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadUpcoming().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (upcomingMovieList.isNullOrEmpty())
                        upcomingSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    upcomingSkeleton.showOriginal()
                    upcomingMovieList.clear()
                    upcomingMovieList.addAll(res.data!!)
                    upcomingAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadPopular().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (popularMovieList.isNullOrEmpty())
                        popularSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    popularSkeleton.showOriginal()
                    popularMovieList.clear()
                    res.data?.let { popularMovieList.addAll(it) }
                    popularAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })

        viewModel.loadTopRated().observe(requireActivity(), Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    if (topRatedMovieList.isNullOrEmpty())
                        topRatedSkeleton.showSkeleton()
                }
                Status.SUCCESS -> {
                    topRatedSkeleton.showOriginal()
                    topRatedMovieList.clear()
                    res.data?.let { topRatedMovieList.addAll(it) }
                    topRatedAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showToast(res.msg.toString())
                }
            }
        })
    }

    @SuppressLint("ResourceType")
    private fun initSkeletons()
    {
        nowPlayingSkeleton = binding.recyclerViewNowPlaying.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )

        upcomingSkeleton = binding.recyclerViewUpcoming.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )

        popularSkeleton = binding.recyclerViewPopular.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )

        topRatedSkeleton = binding.recyclerViewTopRated.applySkeleton(
            R.layout.item_movie_home,
            itemCount = 10
        )
    }


    private fun initAdapters() {
        upcomingAdapter = HomeRecyclerViewAdapter(requireContext(), upcomingMovieList)
        binding.recyclerViewUpcoming.adapter = upcomingAdapter

        popularAdapter = HomeRecyclerViewAdapter(requireContext(), popularMovieList)
        binding.recyclerViewPopular.adapter = popularAdapter

        topRatedAdapter = HomeRecyclerViewAdapter(requireContext(), topRatedMovieList)
        binding.recyclerViewTopRated.adapter = topRatedAdapter

        nowPlayingAdapter = HomeRecyclerViewAdapter(requireContext(), nowPlayingMovies)
        binding.recyclerViewNowPlaying.adapter = nowPlayingAdapter
    }


    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.text_view_all_popular -> {
                val bundle = bundleOf(Constants.viewAll to Constants.Popular)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.text_view_all_top_rated -> {
                val bundle = bundleOf(Constants.viewAll to Constants.TopRated)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.text_view_all_upcoming -> {
                val bundle = bundleOf(Constants.viewAll to Constants.Upcoming)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }
            R.id.text_view_all_now_playing -> {
                val bundle = bundleOf(Constants.viewAll to Constants.NowPlaying)
                navController.navigate(R.id.action_homeFragment_to_viewAllFragment, bundle)
            }


        }

    }

}