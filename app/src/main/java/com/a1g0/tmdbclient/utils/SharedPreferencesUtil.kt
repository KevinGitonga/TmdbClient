package com.a1g0.tmdbclient.utils

import android.content.Context

/**
 * Provides access to SharedPreferences
 */
object SharedPreferenceUtil {

    private const val LAUNCH_STATE_STORE = "LAUNCH_STATE_STORE"
    private const val LAUNCH_STATE_VALUE_KEY = "LAUNCH_STATE_VALUE_KEY"

    fun saveFirstLaunchState(context: Context, isFirstLaunch: Boolean) {
        val sharedPref = context.getSharedPreferences(
            LAUNCH_STATE_STORE, Context.MODE_PRIVATE)

        val editor = sharedPref?.edit()
        editor?.putBoolean(LAUNCH_STATE_VALUE_KEY, isFirstLaunch)
        editor?.apply()
    }

    fun getFirstLaunchState(context: Context): Boolean {
        return context.getSharedPreferences(
            LAUNCH_STATE_STORE, Context.MODE_PRIVATE
        )
            .getBoolean(LAUNCH_STATE_VALUE_KEY, true)
    }
}
