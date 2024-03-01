package ru.netology.nmedia.repository.impl

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository

class PostRepositorySharedPrefsImpl(
    context: Context
) : PostRepository {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"

    private var nextId = 1L
    private var posts = emptyList<Post>()
        set(value){
            field = value
            data.value = value
            sync()
        }
    private val data = MutableLiveData(posts)


    init {
        prefs.getString(key, null)?.let {
            posts = gson.fromJson(it, type)
            nextId = posts.maxOfOrNull { post -> post.id }?.inc() ?: 1
            data.value = posts
        }
    }

    override fun gelAll(): LiveData<List<Post>> = data
    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe, likes = when (it.likedByMe) {
                    false -> it.likes + 1
                    true -> it.likes - 1
                }
            )
        }
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shares = it.shares + 1
            )
        }
    }

    private fun sync() {
        prefs.edit().apply {
            putString(key, gson.toJson(posts))
            apply()
        }
    }
}