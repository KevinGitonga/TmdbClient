package com.a1g0.tmdbclient.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.a1g0.tmdbclient.data.model.Movie
import com.a1g0.tmdbclient.ui.main.viewpager.HomeViewPagerFragment

class HomeViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    val movies: List<Movie> = ArrayList()
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = movies.size

    override fun createFragment(position: Int): Fragment {
        return HomeViewPagerFragment(movies[position])
    }
}
