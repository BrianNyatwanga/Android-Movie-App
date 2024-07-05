import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.movies.streamy.R
import com.movies.streamy.model.dataSource.implementation.authdata

private val ViewDataBinding.itemTitle: Any
    get() {return itemTitle}
private val ViewBinding.itemGenre: Any
    get() {return itemGenre}
private var Any.text: String
    get() {return text}
    set(value) {}

class WatchlistAdapter : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {
    private var watchlist: List<authdata.WatchlistItem> = listOf()

    fun setWatchlist(newWatchlist: List<authdata.WatchlistItem>) {
        this.watchlist = newWatchlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* layoutId = */ R.layout.item_watchlist,
            /* parent = */ parent,
            /* attachToParent = */ false
        )
        return WatchlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.bind(watchlist[position])
    }

    override fun getItemCount(): Int = watchlist.size

    inner class WatchlistViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: authdata.WatchlistItem) {
            binding.itemTitle.text = item.title
            binding.itemGenre.text = item.genre
        }
    }
}
