package com.example.tallerkotlin_prueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkotlin_prueba.Network.Comment
import com.example.tallerkotlin_prueba.Network.UserResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_detalle.view.*

class DetalleAdapter(private var data:List<Comment>, private val listener: DetalleAdapter.CommentHolder.OnAdapterListener) :
    RecyclerView.Adapter<DetalleAdapter.CommentHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val inflatedView = parent.inflate(R.layout.content_detalle, false)
        return CommentHolder(inflatedView)
    }
    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    fun updateList(postList: List<Comment>) {
        this.data = postList
        this.notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val feed : Comment = this.data[position]

        Picasso.get()
            .load(feed.user_image)
            .resize(100,100)
            .centerCrop()
            .into(holder.itemView.item_image_comment)
        holder.itemView.item_comment.text = feed.comment

        //holder.itemView.item_detail_lastname.text = feed.lastname
        //holder.itemView.item_detail_imagen.textAlignment=feed.image
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.itemView.item_detail_imagen)
        //GLIDE O PICASSO PARA IMAGEVIEW
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class CommentHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var feed : Comment? = null

        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: Comment)
        }

    }
}