package com.yjy.presentation.feature.example

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.yjy.domain.model.GithubRepo
import com.yjy.domain.model.Joke
import com.yjy.presentation.R
import com.yjy.presentation.base.BaseActivity
import com.yjy.presentation.databinding.ActivityExampleBinding
import com.yjy.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExampleActivity : BaseActivity<ActivityExampleBinding>(R.layout.activity_example) {

    private val exampleViewModel: ExampleViewModel by viewModels()
    private val githubRepoItemClickListener = object : GithubRepoItemClickListener {
        override fun onItemClicked(githubRepo: GithubRepo) {
            showToast(githubRepo.repoName)
        }
    }
    private val githubRepoAdapter = GithubRepoAdapter(githubRepoItemClickListener)

    override fun initViewModel() {
        binding.exampleViewModel = exampleViewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerViewGithubRepos.adapter = githubRepoAdapter
    }

    override fun setListener() {
        githubRepoAdapter.addLoadStateListener { loadStates ->
            when(loadStates.refresh) {
                is LoadState.Loading -> {
                    binding.progressGithubRepos.isVisible = true
                    binding.buttonGetGithubRepos.isEnabled = false
                }
                is LoadState.NotLoading -> {
                    binding.progressGithubRepos.isVisible = false
                    binding.buttonGetGithubRepos.isEnabled = true
                }
                is LoadState.Error -> {
                    binding.progressGithubRepos.isVisible = false
                    binding.buttonGetGithubRepos.isEnabled = true
                    val errorState = loadStates.refresh as LoadState.Error
                    showToast(errorState.error.localizedMessage ?: "")
                }
            }
        }
    }

    override fun observeFlows() {
        collectLatestFlow(exampleViewModel.number) {
            binding.textViewDataStoreValue.text = it.toString()
        }
        collectLatestFlow(exampleViewModel.githubRepos) {
            githubRepoAdapter.submitData(it)
        }
    }

    override fun observeStateFlows() {
        collectLatestStateFlow(exampleViewModel.joke) { state ->
            when(state) {
                is UiState.Ready -> {
                    binding.progressJoke.isVisible = false
                    binding.textViewJoke.isVisible = true
                    binding.buttonGetJoke.isEnabled = true
                    binding.textViewJoke.text = ""
                }
                is UiState.Loading -> {
                    binding.progressJoke.isVisible = true
                    binding.textViewJoke.isVisible = false
                    binding.buttonGetJoke.isEnabled = false
                }
                is UiState.Success<*> -> {
                    binding.progressJoke.isVisible = false
                    binding.textViewJoke.isVisible = true
                    binding.buttonGetJoke.isEnabled = true
                    binding.textViewJoke.text = (state.data as Joke).content
                }
                is UiState.Error -> {
                    binding.progressJoke.isVisible = false
                    binding.textViewJoke.isVisible = true
                    binding.buttonGetJoke.isEnabled = true
                    // 에러 메시지를 지속적으로 표기해야 되는 경우에만..
                    // 토스트 메시지 같은걸 여기서 설정하면 CC가 일어날때마다 계속 메시지가 발생함으로 안됨.
                    binding.textViewJoke.text = state.message
                }
            }
        }
    }

    override fun observeSharedFlow() {

        collectLatestSharedFlow(exampleViewModel.message) {
            when(it) {
                is ExampleViewModel.ExampleMessage.GetJokeFailed -> showToast(it.message ?: "Joke 받아오기 실패")
            }
        }
    }
}