package com.movies.streamy.view.series.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.ItemSeriesBinding
import com.movies.streamy.model.dataSource.network.data.response.SeriesTrending

class TrendingSeriesAdapter(private val clicked: (SeriesTrending) -> Unit) :
    ListAdapter<SeriesTrending, TrendingSeriesAdapter.SeriesViewHolder>(SeriesDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        context = parent.context
        val binding = ItemSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = getItem(position)
        holder.bind(series)
    }

    inner class SeriesViewHolder(private val binding: ItemSeriesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SeriesTrending?) {
            binding.executePendingBindings()

            binding.apply {
                root.setOnClickListener {
                    data?.let { clicked(it) }

                }
                val posterUrl = "https://image.tmdb.org/t/p/w500${data!!.posterPath}"
                Glide.with(imageItem.context)
                    .load(posterUrl)
                    .into(imageItem)

                val voteAverage = String.format("%.1f", data!!.voteAverage!!)

                movieRating.text = voteAverage
                title.text = data.name
            }
        }
    }

    private class SeriesDiffCallback : DiffUtil.ItemCallback<SeriesTrending>() {
        override fun areItemsTheSame(oldItem: SeriesTrending, newItem: SeriesTrending): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SeriesTrending, newItem: SeriesTrending): Boolean {
            return oldItem == newItem
        }
    }
}
