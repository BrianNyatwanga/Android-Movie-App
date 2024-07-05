package com.movies.streamy.view.movies.adapters;

import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.RowMovieItemBinding
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult

class PopularMovieAdapter(
        private val onItemClicked: (PopularMovieResult) -> Unit
) :
        RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder>(
        ) {


        private val popularMovieDiffCallback = object: DiffUtil.ItemCallback<PopularMovieResult>() {
                override fun areItemsTheSame(oldItem: PopularMovieResult, newItem: PopularMovieResult): Boolean {
                        return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: PopularMovieResult, newItem: PopularMovieResult): Boolean {
                        return oldItem == newItem
                }
        }

        val asyncList = AsyncListDiffer(this, popularMovieDiffCallback)
        override fun onBindViewHolder(holder: PopularMovieViewHolder, position: Int) {
                val data = asyncList.currentList[position]
                holder.bind(data)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMovieViewHolder {
                val binding = RowMovieItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                )
                return PopularMovieViewHolder(binding)
        }

        override fun getItemCount() = asyncList.currentList.size

        inner class PopularMovieViewHolder(
                private val binding: RowMovieItemBinding,
        ) : RecyclerView.ViewHolder(binding.root) {
                fun bind(data: PopularMovieResult) {
                        binding.apply {
                                root.setOnClickListener {
                                        onItemClicked(data)
                                }
                                tvTitle.text = data.title

                                val posterUrl = "https://image.tmdb.org/t/p/w500${data.posterPath}"
                                Glide.with(posterImageView.context)
                                        .load(posterUrl)
                                        .into(posterImageView)
                                val voteAverage = String.format("%.1f", data!!.voteAverage!!)

                                movieRating.text = voteAverage
                        }
                }
        }


}
