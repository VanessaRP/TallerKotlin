package com.example.tallerkotlin_prueba

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tallerkotlin_prueba.Network.Post
import com.example.tallerkotlin_prueba.Network.Repository
import com.example.tallerkotlin_prueba.Network.UserResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_amigos.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AmigosActivity : AppCompatActivity(), AmigosAdapter.UserHolder.OnAdapterListener  {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: AmigosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amigos)

        adapter = AmigosAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        amigosRecyclerView.layoutManager= linearLayoutManager
        amigosRecyclerView.adapter = adapter

        callService()
    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getUser()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val posts : List<UserResponse>?  = response.body()
                        if( posts != null) updateInfo(posts)
                    }else{
                        Toast.makeText(this@AmigosActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@AmigosActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<UserResponse>) {
        adapter.updateList(list)
    }
    override fun onItemClickListener(item: UserResponse) {
        Snackbar.make(AmigosLayout, "Para comunicarte con ${item.name} puedes comunicarte al ${item.phone}", Snackbar.LENGTH_LONG).show()
        //Toast.makeText(this, "Click item ${item.username}", Toast.LENGTH_LONG).show()

        val postString : String = Gson().toJson(item, UserResponse::class.java)
        Log.d("GSON Class to String", postString )
        /**
         * puedes enviar los extras a una pantalla de detalle
         */

        val post : UserResponse = Gson().fromJson(postString, UserResponse::class.java)
        Log.d("GSON string to class", post.username )
    }
}