package com.a1g0.tmdbclient.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Base Activity which binds [ViewModel] [VM] ans [ViewBinding] [VB]
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {

    protected abstract val mViewModel : VM

    protected abstract var mViewBinding : VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = getViewBinding()

    }

    /**
     * It returns [VB] which is assigned to [mViewBinding] ans used in [onCreate]
     */
    abstract fun getViewBinding() : VB

}