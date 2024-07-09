package com.movies.streamy.view.movies.moviedetails


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.movies.streamy.BuildConfig
import com.movies.streamy.R
import com.movies.streamy.databinding.MovieDetailsItemBinding
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult
import com.movies.streamy.model.dataSource.network.data.response.homeData.TrailerResult
import com.movies.streamy.view.MainActivity
import com.movies.streamy.view.movies.MoviesViewModel
import com.movies.streamy.view.movies.adapters.NowPlayingMovieAdapter

class NowPlayingMovieDetailsFragment : Fragment(){

    private val movieName = "movie"
    private var movie: NowPlayingMovieResult? = null
    private lateinit var binding: MovieDetailsItemBinding
//    private lateinit var viewModel: MoviesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        arguments?.let {
            movie = it.getSerializable(movieName) as NowPlayingMovieResult
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailsItemBinding.inflate(inflater, container, false)
//        binding.viewModel = viewModel
        binding.backPress.setOnClickListener{

            val intent = Intent(requireContext(), MainActivity::class.java)
            requireContext().startActivity(intent)
        }
        val voteAverage = String.format("%.1f", movie!!.voteAverage!!)

        binding.apply {
            MovieTitle.text = movie!!.title
            MovieRating.text = voteAverage
            MovieTitle2.text = movie!!.title
            voteCount.text = movie!!.voteCount.toString()
            overview.text = movie!!.overview
            releaseDate.text =movie!!.releaseDate

            Glide.with(binding.detailsPoster.context).load(BuildConfig.POSTER_URL + movie!!.posterPath).into(binding.detailsPoster)
        }
//        binding.playButton.setOnClickListener {
//            movie?.let { item ->
//                item.id?.let { movieId ->
//                    viewModel.setTrailerVisible(true)
//                    viewModel.getTrailerByMovieId(movieId)
//                }
//            }
//        }
//        binding.frameOne.visibility = View.GONE

//        viewModel.trailerList.observe(viewLifecycleOwner, Observer { trailerList ->
//            if (viewModel.trailerVisible.value == true) {
//                trailerList.firstOrNull()?.let { trailer ->
//                    playTrailer(trailer)
//                } ?: run {
//                    Toast.makeText(requireContext(), "Trailer not found", Toast.LENGTH_SHORT).show()
//                }
//                viewModel.setTrailerVisible(false)  // Reset trailer visibility after attempting to play
//            }
//        })
        return binding.root
    }

    companion object{
        fun newInstance(movie: NowPlayingMovieResult) = NowPlayingMovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(movieName, movie )
            }
        }
    }

//    private fun playTrailer(trailer: TrailerResult) {
//        val trailerUrl = "https://www.youtube.com/watch?v=${trailer.key}"
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
//        intent.putExtra("force_fullscreen", true)
//        startActivity(intent)
//        viewModel.setTrailerVisible(false)
//    }


}
