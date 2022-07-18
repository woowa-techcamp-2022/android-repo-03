package com.example.gitreposearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitreposearch.data.Repo
import com.example.gitreposearch.databinding.RvSearchRowBinding

class SearchRecyclerViewAdapter :
    PagingDataAdapter<Repo.Item, SearchRecyclerViewAdapter.ViewHolder>(diffUtil) {

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Repo.Item>(){
            override fun areItemsTheSame(oldItem: Repo.Item, newItem: Repo.Item): Boolean {
                return oldItem.owner == newItem.owner
            }

            override fun areContentsTheSame(oldItem: Repo.Item, newItem: Repo.Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(private val binding: RvSearchRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repo.Item) {
            with(binding) {
                Glide.with(root)
                    .load(item.owner.avatarUrl)
                    .circleCrop()
                    .into(ivSearchRowAvatar)
                tvSearchRowLogin.text = item.owner.login
                tvSearchRowName.text = item.name
                tvSearchRowDescription.text = item.description
                tvSearchRowStargazersCount.text = item.stargazersCount.toString()
                tvSearchRowLanguage.text = item.language
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvSearchRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


}