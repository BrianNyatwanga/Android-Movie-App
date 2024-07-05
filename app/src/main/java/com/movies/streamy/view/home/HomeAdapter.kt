//package com.movies.streamy.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.AllMovieSlideItemBinding
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
class HomeAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<HomeAdapter.TransactionsViewHolder>() {
    private lateinit var context: Context

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val data = asyncList.currentList[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        context = parent.context
        return TransactionsViewHolder(
            AllMovieSlideItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = asyncList.currentList.size

    inner class TransactionsViewHolder(
        private val binding: AllMovieSlideItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = asyncList.currentList[position]
                    itemClickListener.onItemClick(item)
                }
            }
        }
        fun bind(data: HomeResult) {
            binding.MovieName.text = data.name ?: data.title

            val Rating = String.format("%.1f", data!!.vote_average!!)
            binding.Rating.text = Rating
            val posterUrl = "https://image.tmdb.org/t/p/w500${data.poster_path}"
            Glide.with(binding.image.context)
                .load(posterUrl)
                .into(binding.image)
        }
    }
    interface OnItemClickListener {
        fun onItemClick(item: HomeResult)
    }
    private val TransactionsDiffCallback = object : DiffUtil.ItemCallback<HomeResult>() {
        override fun areItemsTheSame(oldItem: HomeResult, newItem: HomeResult): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: HomeResult, newItem: HomeResult): Boolean {
            return oldItem == newItem
        }
    }
    val asyncList = AsyncListDiffer(this, TransactionsDiffCallback)
}