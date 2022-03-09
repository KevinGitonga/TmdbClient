package com.a1g0.tmdbclient.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Cast (
    val id: Int,
    val name: String ?= null,
    val original_name: String ?= null,
    val adult: Boolean ?= null,
    val character: String ?= null,
    val also_known_as: ArrayList<String> ?= null,
    val profile_path: String ?= null,
    val gender: Int?= null,
    val known_for_department: String?= null,
    val popularity: Number?= null,
    val credit_id: String?= null,
    val department: String?= null,
    val release_date: String?= null,
    val job: String?= null,
    val vote_count: Int?= null,
    val video: Boolean?= null,
    val vote_average: Number?= null,
    val title: String?= null,
    val genre_ids: List<Int>?= null,
    val original_language: String?= null,
    val original_title: String?= null,
    val backdrop_path: String?= null,
    val overview: String?= null,
    val poster_path: String?= null
) : Parcelable