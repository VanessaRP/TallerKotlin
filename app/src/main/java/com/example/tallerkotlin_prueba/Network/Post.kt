package com.example.tallerkotlin_prueba.Network

data class Post (val id: Int,val username: String, val user_image: String,val image:String, val body:String,  val likes: Int, val comment: List<Comment>)