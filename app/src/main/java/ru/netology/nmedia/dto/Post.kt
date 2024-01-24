package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 10,
    val likedByMe: Boolean = false,
    val shares: Int = 5,
    val views: Int = 5
)