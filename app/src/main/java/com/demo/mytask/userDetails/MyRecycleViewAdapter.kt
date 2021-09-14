package com.demo.mytask.userDetails

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.mytask.R
import com.demo.mytask.WheatherActivity
import com.demo.mytask.database.UserEntity
import com.demo.mytask.databinding.ListItemBinding


class MyRecycleViewAdapter(private val usersList: List<UserEntity>) :
    RecyclerView.Adapter<MyviewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        context = parent.context
        return MyviewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(usersList[position])
        holder.binding.user = usersList[position]

        holder.binding.cardView.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: hello")
            context!!.startActivity(Intent(context!!, WheatherActivity::class.java))
        }

    }

    public fun getUser(pos: Int): UserEntity? {
        return usersList[pos]
    }


}

class MyviewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserEntity) {
        binding.FirstNameTextView.text = user.firstName
        binding.secondNameTextView.text = user.lastName
        binding.userTextField.text = user.userName


    }

}