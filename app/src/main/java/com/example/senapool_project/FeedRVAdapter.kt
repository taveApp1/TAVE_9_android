package com.example.senapool_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.senapool_project.databinding.ItemDiaryFeedBinding

class FeedRVAdapter(private val DiaryList: ArrayList<Diary>): RecyclerView.Adapter<FeedRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick()
    }
    private lateinit var mItemClickListener: FeedRVAdapter.MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FeedRVAdapter.ViewHolder {
        val binding: ItemDiaryFeedBinding = ItemDiaryFeedBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedRVAdapter.ViewHolder, position: Int) {
        holder.bind(DiaryList[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick()
        }
    }

    override fun getItemCount(): Int = DiaryList.size

    inner class ViewHolder(val binding: ItemDiaryFeedBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(diary: Diary){
            binding.itemDiaryFeedUserTv.text=diary.user
            binding.itemDiaryFeedTitleTv.text=diary.title
            binding.itemDiaryFeedContentTv.text=diary.content
            binding.itemDiaryFeedImgIv.setImageResource(diary.img!!)
        }
    }
}