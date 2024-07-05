package com.movies.streamy.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.movies.streamy.BuildConfig
import com.movies.streamy.databinding.MovieDetailsItemBinding
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
import com.movies.streamy.view.MainActivity


    class HomeTrendingDetails : Fragment() {

        private val Details = "details"
        private var details: HomeResult? = null
        private lateinit var binding: MovieDetailsItemBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {

                details = it.getSerializable(Details) as HomeResult
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
            binding.apply {

                Type.text = details!!.media_type
                MovieTitle.text = details!!.title ?: details!!.name
                MovieRating.text = details!!.vote_average.toString()
                MovieTitle2.text = details!!.title ?: details!!.name
                voteCount.text = details!!.vote_count.toString()
                overview.text = details!!.overview
                releaseDate.text =details!!.first_air_date ?: details!!.release_date
                Glide.with(binding.detailsPoster.context).load(BuildConfig.POSTER_URL + details!!.poster_path).into(binding.detailsPoster)
            }
            return binding.root
        }
        companion object{
            fun newInstance(details: HomeResult) = HomeTrendingDetails().apply {
                arguments = Bundle().apply {
                    putSerializable(Details,details)
                }
            }
        }
    }

