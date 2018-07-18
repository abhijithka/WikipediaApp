package com.keralastones.wikipedia

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide


class SearchResultAdapter(private val context: Context, private var searchResults: List<SearchResult>, private val resultItemClickListener: ResultItemListener) :
        RecyclerView
                                                                                                         .Adapter<SearchResultAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return searchResults.size }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.result_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchResult = searchResults[position]
        holder.resultDescription.text = searchResult.description
        holder.title.text = searchResult.title
        holder.item.setOnClickListener({
            resultItemClickListener.onItemClicked(searchResult, context)
        })
        Glide.with(context).load(searchResult.imageUrl).into(holder.resultImage)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var resultImage: ImageView
        lateinit var resultDescription: TextView
        lateinit var title: TextView
        lateinit var item: LinearLayout

        init {
            itemView.apply {
                resultImage = itemView.findViewById<View>(R.id.resultImage) as ImageView
                resultDescription = itemView.findViewById<View>(R.id.resultDescription) as TextView
                title = itemView.findViewById<View>(R.id.title) as TextView
                item = itemView.findViewById<View>(R.id.item) as LinearLayout
            }

        }

    }
}