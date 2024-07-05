package com.movies.streamy.view.movies.adapters

import com.movies.streamy.databinding.RowMovieItemBinding
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NowPlayingMovieAdapter(
    private val onItemClicked: (NowPlayingMovieResult) -> Unit
) :
    RecyclerView.Adapter<NowPlayingMovieAdapter.NowPlayingMovieViewHolder>(
    ) {


    private val nowPlayingMovieDiffCallback = object: DiffUtil.ItemCallback<NowPlayingMovieResult>() {
        override fun areItemsTheSame(oldItem: NowPlayingMovieResult, newItem: NowPlayingMovieResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NowPlayingMovieResult, newItem: NowPlayingMovieResult): Boolean {
            return oldItem == newItem
        }
    }

    val asyncList = AsyncListDiffer(this, nowPlayingMovieDiffCallback)
    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        val data = asyncList.currentList[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val binding = RowMovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NowPlayingMovieViewHolder(binding)
    }

    override fun getItemCount() = asyncList.currentList.size

    inner class NowPlayingMovieViewHolder(
        private val binding: RowMovieItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NowPlayingMovieResult) {
            binding.apply {
                root.setOnClickListener {
                    onItemClicked(data)
                }
                tvTitle.text = data!!.title

                val voteAverage = String.format("%.1f", data!!.voteAverage!!)

                movieRating.text = voteAverage


                val posterUrl = "https://image.tmdb.org/t/p/w500${data.posterPath}"
                Glide.with(posterImageView.context)
                    .load(posterUrl)
                    .into(posterImageView)
            }
        }
    }


}
