package com.movies.streamy.view.home


//package com.movies.streamy.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.PopularMovieItemBinding
import com.movies.streamy.databinding.PopularSeriesItemBinding
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import com.movies.streamy.model.dataSource.network.data.response.SeriesPopular
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
class AdapterPopularMovie(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<AdapterPopularMovie.TransactionsViewHolder>() {
    private lateinit var context: Context

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val data = asyncList.currentList[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        context = parent.context
        return TransactionsViewHolder(
            PopularMovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = asyncList.currentList.size

    inner class TransactionsViewHolder(
        private val binding: PopularMovieItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = asyncList.currentList[position]
                    itemClickListener.onItemMovieClick(item)
                }
            }
        }
        fun bind(data: PopularMovieResult) {
            binding.title.text = data.title ?: data.title

            val Rating = String.format("%.1f", data!!.voteAverage!!)
            binding.movieRating.text = Rating
            val posterUrl = "https://image.tmdb.org/t/p/w500${data.posterPath}"
            Glide.with(binding.posterImageView.context)
                .load(posterUrl)
                .into(binding.posterImageView)
        }
    }
    interface OnItemClickListener {
        fun onItemMovieClick(item: PopularMovieResult)
    }
    private val TransactionsDiffCallback = object : DiffUtil.ItemCallback<PopularMovieResult>() {
        override fun areItemsTheSame(oldItem: PopularMovieResult, newItem: PopularMovieResult): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: PopularMovieResult, newItem: PopularMovieResult): Boolean {
            return oldItem == newItem
        }
    }
    val asyncList = AsyncListDiffer(this, TransactionsDiffCallback)
}