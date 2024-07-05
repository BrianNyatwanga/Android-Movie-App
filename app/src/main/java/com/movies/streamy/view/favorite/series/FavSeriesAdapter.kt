//package com.movies.streamy.view.favorite.series
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.movies.streamy.databinding.ItemFavoriteSeriesBinding
//import com.movies.streamy.room.favorites.FavMovieEntity
//
//class FavoriteSeriesAdapter : ListAdapter<FavMovieEntity, FavoriteSeriesAdapter.ViewHolder>(DiffCallback()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = FavoriteSeriesFragmentBin.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val series = getItem(position)
//        holder.bind(series)
//    }
//
//    class ViewHolder(private val binding: ItemFavoriteSeriesBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(series: FavMovieEntity) {
//            binding.series = series
//            binding.executePendingBindings()
//        }
//    }
//
//    class DiffCallback : DiffUtil.ItemCallback<FavMovieEntity>() {
//        override fun areItemsTheSame(oldItem: FavMovieEntity, newItem: FavMovieEntity): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: FavMovieEntity, newItem: FavMovieEntity): Boolean {
//            return oldItem == newItem
//        }
//    }
//}
