package com.belajar.githubusernavigation.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.belajar.githubusernavigation.DetailedActivity
import com.belajar.githubusernavigation.data.response.ItemsItem
import com.belajar.githubusernavigation.databinding.ItemUserListBinding
import com.bumptech.glide.Glide

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val getList = ArrayList<ItemsItem>()

    class UserViewHolder(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ItemsItem) {
            binding.tvName.text = item.login
            Glide.with(binding.root.context)
                .load(item.avatarUrl)
                .into(binding.cvAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return getList.size
    }

    fun setUserList(newList: List<ItemsItem>) {
        getList.clear()
        getList.addAll(newList)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val viewHolderItem = getList[position]
        holder.bindItem(viewHolderItem)
        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailedActivity::class.java)
            moveIntent.putExtra("id", viewHolderItem.login)
            holder.itemView.context.startActivity(moveIntent)
        }
    }

}