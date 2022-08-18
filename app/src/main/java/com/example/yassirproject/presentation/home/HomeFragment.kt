package com.example.yassirproject.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yassirproject.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.yassirproject.databinding.HomeLayoutBinding
import com.example.yassirproject.domain.model.home.Movie

class HomeFragment: Fragment(R.layout.home_layout) {

    private var _binding: HomeLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()
    private val adapter: HomeAdapter by lazy { HomeAdapter() }
    private lateinit var layoutManager: LinearLayoutManager

    var loadingMore = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeLayoutBinding.inflate(inflater, container, false)
        layoutManager = LinearLayoutManager(this.context)

        setupLists()
        setupObservers()
        setupClickListeners()

        binding.movieList.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItemCount = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount
                    if(!loadingMore){
                        if (visibleItemCount + pastVisibleItemCount >= total) {
                            loadingMore = true
                            viewModel.loadMovies()
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        return binding.root
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it){
                is HomeUiState.Loading -> { renderLoading(state = true) }
                is HomeUiState.LoadingMore -> { renderLoadingMore(state = true) }
                is HomeUiState.Content -> {
                    renderLoading(state = false)
                    renderLoadingMore(state = false)
                    renderContent(it.movies)
                }
                is HomeUiState.Error -> {
                    renderLoading(state = false)
                    renderLoadingMore(state = false)
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.interactions.observe(viewLifecycleOwner) {
            when(it) {
                is HomeActions.Navigate -> { navigate(it.destination) }
            }
        }
    }

    private fun setupClickListeners() {
        binding.retry.setOnClickListener {
            viewModel.loadMovies()
        }
    }

    private fun renderLoading(state: Boolean) {
        if(!state){
            binding.shimmerFrameLayout.stopShimmer()
        }
        binding.shimmerFrameLayout.isVisible = state
        binding.retry.isVisible = !state
    }

    private fun renderContent(movies: List<Movie>) {
        binding.retry.isVisible = false
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.isVisible = false

        if (viewModel.pageCount == 1) adapter.setMovies(movies)
        else adapter.addMovies(movies)
    }

    private fun renderLoadingMore(state: Boolean) {
        binding.progressLoadMore.isVisible = state
        binding.retry.isVisible = !state
        if(!state) loadingMore = state
    }

    private fun setupLists() {
        binding.movieList.adapter = adapter
        binding.movieList.layoutManager = layoutManager
    }

    private fun navigate(destination: NavDirections) {
        findNavController().navigate(destination)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}