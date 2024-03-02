package club.mobile.d21.testmusic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import club.mobile.d21.testmusic.data.Data
import club.mobile.d21.testmusic.databinding.ItemMusicBinding
import com.bumptech.glide.Glide

class MusicAdapter(private val onPlayClick: (Data)->Unit,
    private val onStopClick: (Data)->Unit):
    ListAdapter<Data, MusicAdapter.ViewHolder>(ContactDiffCallback()) {
    inner class ViewHolder(private val binding: ItemMusicBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Data) {
            binding.name.text = data.title
            binding.artist.text = data.artist.name
            Glide.with(binding.root.context)
                .load(data.album.cover)
                .error(R.drawable.ic_downloading)
                .placeholder(R.drawable.ic_downloading)
                .into(binding.image)
            binding.play.setOnClickListener {
                onPlayClick.invoke(data)
            }
            binding.stop.setOnClickListener {
                onStopClick.invoke(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMusicBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    private class ContactDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}
