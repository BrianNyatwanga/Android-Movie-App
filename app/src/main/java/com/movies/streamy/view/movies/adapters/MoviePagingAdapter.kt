package com.movies.streamy.view.movies.adapters

import com.movies.streamy.model.dataSource.network.data.response.NowPlayingMovieResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.streamy.BR
import com.movies.streamy.databinding.RowMovieItemBinding

class MoviePagingAdapter : PagingDataAdapter<NowPlayingMovieResult, MoviePagingAdapter.MyViewHolder>(nowPlayingMovieDiffCallback) {


    var onCLick: ((String) -> Unit)? = null

    companion object {
        private val nowPlayingMovieDiffCallback =
            object : DiffUtil.ItemCallback<NowPlayingMovieResult>() {
                override fun areItemsTheSame(
                    oldItem: NowPlayingMovieResult,
                    newItem: NowPlayingMovieResult
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: NowPlayingMovieResult,
                    newItem: NowPlayingMovieResult
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }


    fun onMovieClick(listener: (String) -> Unit) {
        onCLick = listener
    }

    inner class MyViewHolder(val viewDataBinding: RowMovieItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)

        holder.viewDataBinding.setVariable(BR.movie, data)

        holder.viewDataBinding.root.setOnClickListener {
//            onCLick?.let {
//                it(data?.)
//            }
//            TODO()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RowMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

}