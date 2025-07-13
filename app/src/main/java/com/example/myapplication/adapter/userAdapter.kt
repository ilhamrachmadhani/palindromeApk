package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.User


class UserAdapter(
    private val users: MutableList<User>,
    private val onClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)
        val email = view.findViewById<TextView>(R.id.email)
        val avatar = view.findViewById<ImageView>(R.id.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = "${user.first_name} ${user.last_name}"
        holder.email.text = user.email
        Glide.with(holder.itemView.context).load(user.avatar).circleCrop().into(holder.avatar)
        holder.itemView.setOnClickListener { onClick(user) }
    }

    fun addUsers(newUsers: List<User>) {
        val start = users.size
        users.addAll(newUsers)
        notifyItemRangeInserted(start, newUsers.size)
    }

    fun clearUsers() {
        users.clear()
        notifyDataSetChanged()
    }
}
