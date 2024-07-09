package com.movies.streamy.view.home

//package com.movies.streamy.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.PopularSeriesItemBinding
import com.movies.streamy.model.dataSource.network.data.response.SeriesPopular
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
class AdapterSeries(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<AdapterSeries.TransactionsViewHolder>() {
    private lateinit var context: Context

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val data = asyncList.currentList[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        context = parent.context
        return TransactionsViewHolder(
            PopularSeriesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = asyncList.currentList.size

    inner class TransactionsViewHolder(
        private val binding: PopularSeriesItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = asyncList.currentList[position]
                    itemClickListener.onItemSeriesClick(item)
                }
            }
        }
        fun bind(data: SeriesPopular) {
            binding.seriesName.text = data.name ?: data.name

            val Rating = String.format("%.1f", data!!.voteAverage!!)
            binding.Rating.text = Rating
            val posterUrl = "https://image.tmdb.org/t/p/w500${data.posterPath}"
            Glide.with(binding.image.context)
                .load(posterUrl)
                .into(binding.image)
        }
    }
    interface OnItemClickListener {
        fun onItemSeriesClick(item: SeriesPopular)
    }
    private val TransactionsDiffCallback = object : DiffUtil.ItemCallback<SeriesPopular>() {
        override fun areItemsTheSame(oldItem: SeriesPopular, newItem: SeriesPopular): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: SeriesPopular, newItem: SeriesPopular): Boolean {
            return oldItem == newItem
        }
    }
    val asyncList = AsyncListDiffer(this, TransactionsDiffCallback)
}