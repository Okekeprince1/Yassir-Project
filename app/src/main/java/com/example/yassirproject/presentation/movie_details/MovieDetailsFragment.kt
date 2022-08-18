package com.example.yassirproject.presentation.movie_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.yassirproject.R
import com.example.yassirproject.databinding.MovieDetailsLayoutBinding
import com.example.yassirproject.domain.model.movie_details.MovieDetails
import com.example.yassirproject.utils.getYearFromDate
import com.example.yassirproject.utils.load
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment: Fragment(R.layout.movie_details_layout) {

    private var _binding: MovieDetailsLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailsViewModel by viewModel()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieDetailsLayoutBinding.inflate(inflater, container, false)

        setupToolbar()
        setupObservers()

        viewModel.getMovieDetails(args.id.toString())

        return binding.root
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when(it){
                is MovieDetailsUiState.Loading -> { renderLoading(state = true) }
                is MovieDetailsUiState.Content -> {
                    renderLoading(state = false)
                    renderContent(it.movieDetails)
                }
                is MovieDetailsUiState.Error -> {
                    renderLoading(state = false)
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            toolbarTitle.text = args.movieTitle
        }
    }

    private fun renderLoading(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    private fun renderContent(movieDetails: MovieDetails) {
        binding.apply {
            moviePoster.load(movieDetails.imageUrl)
            movieTitle.text = movieDetails.title
            movieOverview.text = movieDetails.overview
            movieYear.text = movieDetails.year.getYearFromDate()

            val chipInflator = LayoutInflater.from(genresGroup.context)

            movieDetails.genres.forEachIndexed { index, genre ->
                val chip = chipInflator.inflate(R.layout.chip_item, genresGroup, false) as Chip

                chip.id = index
                chip.text = genre
                genresGroup.addView(chip)
            }

            container.isVisible = true
        }
    }
}