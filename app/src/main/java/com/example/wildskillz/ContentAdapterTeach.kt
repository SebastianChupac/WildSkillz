package com.example.wildskillz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wildskillz.model.LearningContent.LearningContent
import androidx.core.content.ContextCompat

class ContentAdapterTeach(
    private var contentList: List<LearningContent>,
    private val onClick: (LearningContent) -> Unit
) : RecyclerView.Adapter<ContentAdapterTeach.ContentViewHolder>() {

    inner class ContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail = view.findViewById<ImageView>(R.id.ivThumbnail)
        val name = view.findViewById<TextView>(R.id.tvContentName)
        val details = view.findViewById<TextView>(R.id.tvContentDetails)
        val price = view.findViewById<TextView>(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_content_teach, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val content = contentList[position]

        holder.name.text = content.name
        holder.thumbnail.setImageResource(content.thumbnailResId)
        holder.price.text = content.price
        val context = holder.itemView.context

        when (content) {
            is LearningContent.Video -> {
                holder.details.text = "${content.author} • ${content.duration} • ${content.views}"
            }
            is LearningContent.Workshop -> {
                holder.details.text = "${content.author} • ${content.time} @ ${content.location}"
            }
        }

    }

    fun updateList(newList: List<LearningContent>) {
        contentList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = contentList.size
}
