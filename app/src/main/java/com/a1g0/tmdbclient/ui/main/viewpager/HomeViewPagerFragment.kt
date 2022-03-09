package com.a1g0.tmdbclient.ui.main.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import coil.load
import com.a1g0.tmdbclient.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.databinding.FragmentHomeViewPagerBinding
import com.a1g0.tmdbclient.utils.Constants

class HomeViewPagerFragment(
    val movie: Movie
) : Fragment() {

    private lateinit var binding : FragmentHomeViewPagerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_view_pager, container, false)
        binding = FragmentHomeViewPagerBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewPagerImage.load(Constants.IMAGE_BASE_URL + movie.backdrop_path) {
            placeholder(Constants.viewPagerPlaceHolder.random())
            error(Constants.viewPagerPlaceHolder.random())
        }
        binding.viewPagerText.text = movie.title

        binding.root.setOnClickListener {
            val bundle = bundleOf(Constants.movie to movie)
            it.findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
        }

    }
}