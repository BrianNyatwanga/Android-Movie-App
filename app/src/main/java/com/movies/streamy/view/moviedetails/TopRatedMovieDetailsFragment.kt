package com.movies.streamy.view.moviedetails


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.movies.streamy.BuildConfig
import com.movies.streamy.databinding.MovieDetailsItemBinding
import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResult
import com.movies.streamy.view.MainActivity

class TopRatedMovieDetailsFragment : Fragment() {

    private val movieName = "movie"
    private var movie: TopRatedMovieResult? = null
    private lateinit var binding: MovieDetailsItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            movie = it.getSerializable(movieName) as TopRatedMovieResult
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MovieDetailsItemBinding.inflate(inflater, container, false)

        binding.backPress.setOnClickListener{
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireContext().startActivity(intent)
        }
//        val voteAverage = floor(movie!!.voteAverage!!)
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
        return binding.root
    }

    companion object{
        fun newInstance(movie: TopRatedMovieResult) = TopRatedMovieDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(movieName, movie )
            }
        }
    }

}
