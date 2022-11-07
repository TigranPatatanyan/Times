package com.example.newyorktimes.presentation.listscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newyorktimes.R
import com.example.newyorktimes.domain.entity.Article
import com.example.newyorktimes.util.Size
import com.example.newyorktimes.util.loadImage

class ArticlesViewAdapter : RecyclerView.Adapter<ArticleViewHolder>() {
    private var articles: ArrayList<Article> = arrayListOf()
    var onItemSelected: ((Article) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Article>) {
        articles.clear()
        articles.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        articles[position].run {
            holder.smallImage.loadImage(articles[position], Size.SMALL)
            holder.title.text = title
            holder.itemView.setOnClickListener {
                onItemSelected?.invoke(this)
            }
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val smallImage: ImageView = itemView.findViewById(R.id.smallImage)
    val title: TextView = itemView.findViewById(R.id.title)
}