package com.yjy.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @BindingAdapter("imageSrc")
    @JvmStatic
    fun setImageUri(imageView: ImageView, imageSrc: String) {
        Glide.with(imageView.context)
            .load(imageSrc)
            .into(imageView)
    }
}