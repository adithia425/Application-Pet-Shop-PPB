package controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.ppb.pawspal.databinding.ItemRowBinding

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ppb.pawspal.R
import data.Item

class ListItemAdapter(private val listItem: ArrayList<Item>) : RecyclerView.Adapter<ListItemAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    interface OnItemClickCallBack{
        fun onItemClick(data: Item)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listItem[position])
        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClick(listItem[holder.adapterPosition])
        }
    }

    fun setOnItemClickCallback(callback: OnItemClickCallBack){
        onItemClickCallBack = callback
    }

    inner class ListViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.photo)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgItemPhoto)

                tvItemName.text = item.name
                tvItemDescription.text = item.description
            }
        }
    }
}