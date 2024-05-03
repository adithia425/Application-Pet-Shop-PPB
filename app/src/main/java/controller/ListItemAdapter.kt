package controller

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ppb.pawspal.databinding.ItemRowBinding
import data.Item

class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private var listItem: List<Item> = emptyList()

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
        Log.e("Init", "OnItemClick")
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
                tvItemPrice.text = "Rp. " + item.price
                tvItemDescription.text = item.description
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItemData(data: List<Item>){
        listItem = data
        notifyDataSetChanged()
    }
}