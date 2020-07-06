package com.example.tallerkotlin_prueba

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tallerkotlin_prueba.Network.Comment
import com.example.tallerkotlin_prueba.Network.Post
import com.example.tallerkotlin_prueba.Network.Repository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.*
import retrofit2.HttpException

class FeedActivity : AppCompatActivity(), RecyclerAdapter.FeedHolder.OnAdapterListener {

    companion object{
        const val KEY_USUARIO="user"
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        adapter = RecyclerAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager= linearLayoutManager
        recyclerView.adapter = adapter

        callService()

        }
    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getPost()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val posts : List<Post>?  = response.body()
                        if( posts != null) updateInfo(posts)
                    }else{
                        Toast.makeText(this@FeedActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@FeedActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<Post>) {
        adapter.updateList(list)
    }
    override fun onItemClickListener(item: Post) {
        val intent = Intent(this, DetalleActivity::class.java)
        startActivity(intent)


        val sharedPref=applicationContext.getSharedPreferences("TALLER_KOTLIN_GSON", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor=sharedPref.edit()
        Toast.makeText(this, "Click item ${item.username}", Toast.LENGTH_LONG).show()

        val gson  = Gson().toJson(item.comment, Comment::class.java)
        Log.d("GSON Class to String", gson )
        editor.putString(FeedActivity.KEY_USUARIO,gson)

    }
    }
