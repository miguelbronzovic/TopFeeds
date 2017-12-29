/*
 * Created by Miguel Bronzovic
 *
 * Children's adapter
 */
package com.topfeeds.sample.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.topfeeds.sample.R
import com.topfeeds.sample.helper.DateHelper
import com.topfeeds.sample.model.Children
import kotlinx.android.synthetic.main.children_item_row.view.*

interface ThumbnailItemClickListener {
    fun onThumbnailClick(url: String)
}

class ChildrenAdapter(val thumbnailListener: ThumbnailItemClickListener) : RecyclerView
.Adapter<ChildrenAdapter.ChildrenViewHolder>() {

    private var values = ArrayList<Children>()
    private val listener = thumbnailListener
    /**
     * Gets current items set
     */
    val childrenItems: List<Children>
        get() = values

    /**
     * Adds first items set to adapter
     */
    fun addNewItems(items: List<Children>) {
        values = items as ArrayList<Children>

        notifyDataSetChanged()
    }

    /**
     * Inserts new retrieved items to adapter
     */
    fun insertItems(items: List<Children>) {
        values.addAll(items)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.children_item_row, parent, false)

        return ChildrenViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        holder.bindTopNews(values[position])
    }

    override fun getItemCount(): Int {
        return values.size
    }

    class ChildrenViewHolder(view: View, val listener: ThumbnailItemClickListener) :
            RecyclerView.ViewHolder(view) {
        fun bindTopNews(childrenItem: Children) {

            with(childrenItem) {
                Picasso.with(itemView.context).load(childrenItem.data.thumbnail).into(itemView.thumbnail)
                itemView.author.text = childrenItem.data.author
                itemView.comments.text = "${childrenItem.data.num_comments} comments"
                itemView.created.text = "${DateHelper.calculateElapsedTimeFromNow(childrenItem.data.created_utc)} Hs"
                itemView.title.text = childrenItem.data.title
                itemView.thumbnail.setOnClickListener { v ->
                    childrenItem.data.preview?.images?.get(0)?.source?.let { listener.onThumbnailClick(it.url) }
                }
            }
        }
    }
}