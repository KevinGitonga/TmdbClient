package com.a1g0.tmdbclient.utils

import android.content.Context
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

/**
 * Network Utility to detect Availability or Unavailability of Internet
 */
object NetworkUtils: ConnectivityManager.NetworkCallback() {

    /**
     * Checks if there is an active internet connection on this device
     *
     * @return true if the device is connected to the internet;
     * false otherwise
     */
    fun isOnline(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= 29) {
                val network = cm.activeNetwork
                val networkCapabilities = cm.getNetworkCapabilities(network)
                (network != null
                        && networkCapabilities != null
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)))
            } else {
                val netInfo = cm.activeNetworkInfo
                netInfo != null
                        && netInfo.state == NetworkInfo.State.CONNECTED
                        && (netInfo.type == ConnectivityManager.TYPE_WIFI
                        || netInfo.type == ConnectivityManager.TYPE_MOBILE)
            }
        } catch (e: Exception) {
            Timber.e(e)
            false
        }
    }

}