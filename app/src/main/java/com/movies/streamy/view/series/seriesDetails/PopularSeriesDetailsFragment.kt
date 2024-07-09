package com.movies.streamy.view.series.seriesDetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.movies.streamy.BuildConfig
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentSeriesDetailsBinding
import com.movies.streamy.model.dataSource.network.data.response.SeriesLatest
import com.movies.streamy.model.dataSource.network.data.response.SeriesPopular
import com.movies.streamy.view.MainActivity

class PopularSeriesDetailsFragment : Fragment() {
    private val SeriesName = "series"
    private var series: SeriesPopular? = null
    private lateinit var binding: FragmentSeriesDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            series = it.getSerializable(SeriesName) as SeriesPopular
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSeriesDetailsBinding.inflate(inflater, container, false)

        binding.backPress.setOnClickListener {

            val intent = Intent(requireContext(), MainActivity::class.java)
            requireContext().startActivity(intent)
        }
//
        val voteAverage = String.format("%.1f", series!!.voteAverage!!)

        binding.apply {
            SeriesTitle.text = series!!.name
            SeriesRating.text = voteAverage
            SeriesName.text = series!!.originalName
            voteCount.text = series!!.voteCount.toString()
            overview.text = series!!.overview
            AiredDate.text = series!!.firstAirDate

            Glide.with(binding.detailsPoster.context)
                .load(BuildConfig.POSTER_URL + series!!.posterPath).into(binding.detailsPoster)
        }
        return binding.root
    }

    companion object
    {
        fun newInstance(series: SeriesPopular) = PopularSeriesDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(SeriesName,series )
            }
        }
    }
}



