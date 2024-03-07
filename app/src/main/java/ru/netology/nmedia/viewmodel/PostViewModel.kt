package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.impl.PostRepositoryFileImpl

val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.getAll()
    val singleData = MutableLiveData(listOf(empty))
    val edited = MutableLiveData(empty)

    init {

    }

    fun save() {
        edited.value?.let {
            repository.save(it)
            if (singleData.value?.get(0)?.id == it.id) {
                singleData.value = listOf(it)
            }
        }
        reset()
    }

    fun reset() {
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

    fun goToPost(post: Post) {
        singleData.value = listOf(post)
    }

    fun removeById(id: Long) = repository.removeById(id)
}