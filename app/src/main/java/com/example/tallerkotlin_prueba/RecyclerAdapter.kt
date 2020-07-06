package com.example.tallerkotlin_prueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkotlin_prueba.Network.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_feed.view.*
class RecyclerAdapter(private var data:List<Post>, private val listener: FeedHolder.OnAdapterListener):
    RecyclerView.Adapter<RecyclerAdapter.FeedHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val inflatedView = parent.inflate(R.layout.content_feed, false)
        return FeedHolder(inflatedView)
    }
    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    fun updateList(postList: List<Post>) {
        this.data = postList
        this.notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val feed : Post = this.data[position]

        Picasso.get()
            .load(feed.user_image)
            .resize(100,100)
            .centerCrop()
            .into(holder.itemView.item_image_profile)
        holder.itemView.item_user_profile.text = feed.username
        holder.itemView.item_image_description.text = feed.body
        Picasso.get()
            .load(feed.image)
            .resize(100,100)
            .centerCrop()
            .into(holder.itemView.item_image)
        //holder.itemView.item_detail_lastname.text = feed.lastname
        //holder.itemView.item_detail_imagen.textAlignment=feed.image
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.itemView.item_detail_imagen)
        //GLIDE O PICASSO PARA IMAGEVIEW
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class FeedHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var feed : Feed? = null

        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: Post)
        }

    }
}