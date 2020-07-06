package com.example.curso_kotlin.network
import com.example.tallerkotlin_prueba.Network.Post
import com.example.tallerkotlin_prueba.Network.Social
import com.example.tallerkotlin_prueba.Network.UserResponse
import retrofit2.Response
import retrofit2.http.GET
interface Service {
    @GET("profile")
    suspend fun getProfile() :Response<UserResponse>
    @GET("posts")
    suspend fun getPost() :Response<List<Post>>
    @GET("users")
    suspend fun getUser() :Response<List<UserResponse>>
}