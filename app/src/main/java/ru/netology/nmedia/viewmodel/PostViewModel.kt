package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.impl.PostRepositoryInMemoryImpl

val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false
)
class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.gelAll()
    val edited = MutableLiveData(empty)
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }
    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun edit(post: Post) {
        edited.value = post
    }
    fun removeById(id: Long) = repository.removeById(id)
}