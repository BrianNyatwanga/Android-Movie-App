package com.movies.streamy.view.movies.adapters


import com.movies.streamy.model.dataSource.network.data.response.TopRatedMovieResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.RowMovieItemBinding
import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult

class TopRatedMovieAdapter(
    private val onItemClicked: (TopRatedMovieResult) -> Unit
) :
    RecyclerView.Adapter<TopRatedMovieAdapter.TopRatedMovieViewHolder>(
    ) {


    private val topRatedMovieDiffCallback = object: DiffUtil.ItemCallback<TopRatedMovieResult>() {
        override fun areItemsTheSame(oldItem: TopRatedMovieResult, newItem: TopRatedMovieResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TopRatedMovieResult, newItem: TopRatedMovieResult): Boolean {
            return oldItem == newItem
        }
    }

    val asyncList = AsyncListDiffer(this, topRatedMovieDiffCallback)
    override fun onBindViewHolder(holder: TopRatedMovieViewHolder, position: Int) {
        val data = asyncList.currentList[position]
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedMovieViewHolder {
        val binding = RowMovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TopRatedMovieViewHolder(binding)
    }

    override fun getItemCount() = asyncList.currentList.size

    inner class TopRatedMovieViewHolder(
        private val binding: RowMovieItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TopRatedMovieResult) {
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
