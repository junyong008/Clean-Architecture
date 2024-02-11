package com.yjy.presentation.feature.example

import androidx.activity.viewModels
import com.yjy.presentation.R
import com.yjy.presentation.base.BaseActivity
import com.yjy.presentation.databinding.ActivityExampleBinding

class ExampleActivity : BaseActivity<ActivityExampleBinding>(R.layout.activity_example) {

    private val exampleViewModel: ExampleViewModel by viewModels()

    override fun initViewModel() {
        binding.exampleViewModel = exampleViewModel
    }

}