package com.movies.streamy.view.series.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.ItemSeriesBinding
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesId

class SeriesAdapter(private val clicked: (TvSeries: TvSeriesId) -> Unit) :
    ListAdapter<TvSeriesId, SeriesAdapter.SeriesViewHolder>(SeriesDiffCallback()) {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        context = parent.context
        val binding = ItemSeriesBinding.inflate(LayoutInflater.from(context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class SeriesViewHolder(private val binding: ItemSeriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TvSeriesId?) {

            binding.executePendingBindings()



            binding.root.setOnClickListener {
                data?.let { clicked.invoke(it) }
            }
        }
    }

    private class SeriesDiffCallback : DiffUtil.ItemCallback<TvSeriesId>() {
        override fun areItemsTheSame(oldItem: TvSeriesId, newItem: TvSeriesId): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvSeriesId, newItem: TvSeriesId): Boolean {
            return oldItem == newItem
        }
    }
}
