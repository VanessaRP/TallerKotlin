package com.example.tallerkotlin_prueba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkotlin_prueba.Network.Post
import com.example.tallerkotlin_prueba.Network.UserResponse
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_amigos.*
import kotlinx.android.synthetic.main.activity_amigos.view.*
import kotlinx.android.synthetic.main.content_amigos.view.*

class AmigosAdapter(private var data:List<UserResponse>, private val listener: AmigosAdapter.UserHolder.OnAdapterListener):
    RecyclerView.Adapter<AmigosAdapter.UserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflatedView = parent.inflate(R.layout.content_amigos, false)
        return UserHolder(inflatedView)
    }
    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    fun updateList(postList: List<UserResponse>) {
        this.data = postList
        this.notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val feed : UserResponse = this.data[position]

        Picasso.get()
            .load(feed.image)
            .resize(100,100)
            .centerCrop()
            .into(holder.itemView.item_image_amigos)
        holder.itemView.item_amigos.text = feed.name+" "+feed.lastname

        //holder.itemView.item_detail_lastname.text = feed.lastname
        //holder.itemView.item_detail_imagen.textAlignment=feed.image
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.itemView.item_detail_imagen)
        //GLIDE O PICASSO PARA IMAGEVIEW
    }
    override fun getItemCount(): Int {
        return data.size
    }
    class UserHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var feed : UserResponse? = null

        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v != null) {
                Snackbar.make(v.AmigosLayout, "Para comunicarte con ${feed?.name} puedes comunicarte al ${feed?.phone}", Snackbar.LENGTH_LONG).show()

            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: UserResponse)
        }

    }
}