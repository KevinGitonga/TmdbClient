package com.a1g0.tmdbclient.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Movie (
    val id: Int,
    val vote_count: Int ?= null,
    val runtime: Int ?= null,
    val poster_path: String ?= null,
    val overview: String ?= null,
    val release_date: String ?= null,
    val title: String ?= null,
    val backdrop_path: String ?= null,
    val popularity: Number ?= null,
    val vote_average: Double ?= null,
    val genre_ids: List<Int> ?= null,
    val genre_names: List<String> ?= null,
    val genres: @RawValue List<Genre> ?= null,
    val video : Boolean ?= null,
    val videos: @RawValue Video?= null
) : Parcelable