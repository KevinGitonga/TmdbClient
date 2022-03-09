package com.a1g0.tmdbclient.utils

import java.util.*

/**
 * Returns [Boolean] based on current time.
 * Returns true if hours are between 06:00 pm - 05:00 am
 */
fun isNight(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (currentHour <= 5 || currentHour >= 18)
}

fun toHours(minutes: Int) : String {
    var min = minutes
    var time : String = ""
    if (min > 0) {
        time = "${min/60} hrs"
        min = min%60
    }
    if (min > 0) {
        time = "$time $min mins"
    }
    return time
}