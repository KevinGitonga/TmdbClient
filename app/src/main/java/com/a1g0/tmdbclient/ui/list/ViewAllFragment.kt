package com.a1g0.tmdbclient.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.a1g0.tmdbclient.R
import com.faltenreich.skeletonlayout.Skeleton
import dagger.hilt.android.AndroidEntryPoint
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.databinding.FragmentViewAllBinding
import com.a1g0.tmdbclient.ui.adapter.ViewAllRecyclerViewAdapter
import com.a1g0.tmdbclient.utils.Constants
import com.a1g0.tmdbclient.utils.NetworkUtils
import com.a1g0.tmdbclient.utils.showToast
import com.faltenreich.skeletonlayout.applySkeleton

@AndroidEntryPoint
class ViewAllFragment : Fragment() {

    private lateinit var binding: FragmentViewAllBinding
    private val viewModel: ViewAllViewModel by viewModels()

    private lateinit var movieAdapter: ViewAllRecyclerViewAdapter
    private lateinit var movieSkeleton: Skeleton

    private var moviesList: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_all, container, false)
        binding = FragmentViewAllBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieAdapter = ViewAllRecyclerViewAdapter()
        binding.movieRecyclerView.adapter = movieAdapter

        movieSkeleton = binding.movieRecyclerView.applySkeleton(R.layout.item_search, 15)

        val pageType = requireArguments().get(Constants.viewAll)
        binding.pageTitle.text = pageType.toString()

        if(NetworkUtils.isOnline(requireContext())){
            when(pageType) {
                Constants.Upcoming -> fetchUpcoming()
                Constants.TopRated -> fetchTopRated()
                Constants.Popular -> fetchPopular()
                Constants.NowPlaying -> fetchNowPlaying()
            }
        }
        else{
            showToast(getString(R.string.check_network_to_continue), Toast.LENGTH_LONG)
        }

        binding.buttonBack.setOnClickListener {
            binding.root.findNavController().navigateUp()
        }

    }

    private fun fetchNowPlaying() {
        viewModel.fetchNowPlaying().observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }


    private fun fetchPopular() {
        viewModel.fetchPopular().observe(viewLifecycleOwner) {
            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun fetchTopRated() {

        viewModel.fetchTopRated().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

    private fun fetchUpcoming() {

        viewModel.fetchUpcoming().observe(viewLifecycleOwner) {

            movieAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        }

    }

}