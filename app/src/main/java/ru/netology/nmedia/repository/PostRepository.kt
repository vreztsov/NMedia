package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
//    fun get(): LiveData<Post>
    fun gelAll(): LiveData<List<Post>>
    fun likeById(id: Long)
//    fun like()
//    fun share()
}