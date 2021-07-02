package com.example.yoginews.views

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yoginews.data.model.NewsModel
import com.example.yoginews.databinding.NewsRvItemBinding
import com.example.yoginews.utils.StaticVariables.Companion.WEB_URL

class NewsAdapter(private val newsList: ArrayList<NewsModel>) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return NewsRvItemBinding.inflate(LayoutInflater.from(parent.context)).let {
            MyViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = newsList[position]
        holder.binding.apply {
            this.item = news
        }
        holder.itemView.setOnClickListener {
            Intent(context, WebViewActivity::class.java).let {
                it.putExtra(WEB_URL, news.webLink)
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int = newsList.size
    class MyViewHolder(val binding: NewsRvItemBinding) : RecyclerView.ViewHolder(binding.root)

}