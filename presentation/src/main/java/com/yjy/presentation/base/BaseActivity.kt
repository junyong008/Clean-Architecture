package com.yjy.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) :
    AppCompatActivity() {

    protected lateinit var binding: T
    private var toast: Toast? = null

    protected open val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toast?.cancel()
        toast = Toast.makeText(this, message, duration).apply { show() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        initViewModel()
        initView(savedInstanceState)
        setListener()
        setObserver()
        setEventObserver()
    }

    protected open fun initViewModel() {}
    protected open fun initView(savedInstanceState: Bundle?) {}
    protected open fun setListener() {}
    protected open fun setObserver() {}
    protected open fun setEventObserver() {}
}