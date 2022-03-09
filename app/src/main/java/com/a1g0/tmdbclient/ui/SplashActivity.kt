package com.a1g0.tmdbclient.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.a1g0.tmdbclient.R
import com.a1g0.tmdbclient.ui.main.MainActivity
import com.a1g0.tmdbclient.utils.NetworkUtils
import com.a1g0.tmdbclient.utils.SharedPreferenceUtil
import com.a1g0.tmdbclient.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!SharedPreferenceUtil.getFirstLaunchState(this)) {
            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)
                MainActivity.start(this@SplashActivity)
                finish()
            }
        } else if (SharedPreferenceUtil.getFirstLaunchState(this) && !NetworkUtils.isOnline(this)) {
            showToast(getString(R.string.first_time_user_network_notification), Toast.LENGTH_SHORT)
            lifecycleScope.launch {
                delay(2000)
                finish()
            }
        } else if (SharedPreferenceUtil.getFirstLaunchState(this) && NetworkUtils.isOnline(this)) {
            SharedPreferenceUtil.saveFirstLaunchState(this, false)
            lifecycleScope.launch(Dispatchers.IO) {
                delay(2000)
                MainActivity.start(this@SplashActivity)
                finish()
            }
        }
    }
}
