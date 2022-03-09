package com.a1g0.tmdbclient.data.model

data class MovieResponse  (
    val page : Int,
    val results : List<Movie>,
    val total_results: Int,
    val total_pages: Int
)