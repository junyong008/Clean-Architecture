package com.yjy.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutResId: Int) : Fragment() {

    protected lateinit var binding: T
    private var toast: Toast? = null

    protected fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), message, duration).apply { show() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        initViewModel()
        initView(savedInstanceState)
        setListener()
        setObserver()
        setEventObserver()

        return binding.root
    }

    protected open fun initViewModel() {}
    protected open fun initView(savedInstanceState: Bundle?) {}
    protected open fun setListener() {}
    protected open fun setObserver() {}
    protected open fun setEventObserver() {}
}