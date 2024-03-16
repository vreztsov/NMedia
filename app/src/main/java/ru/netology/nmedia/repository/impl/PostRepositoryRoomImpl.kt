package ru.netology.nmedia.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.util.NMediaUtils

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {

    override fun getAll(): LiveData<List<Post>> = dao.getAllPosts().map {list ->
        list.map { it.toDto() }
    }
//        dao.getAllWithQuery().map { list ->
//        list.map { it.toDto() }
//    }

    override fun save(post: Post) {
        dao.save(
            PostEntity.postFromDto(
                if (post.id == 0L) post.copy(
                    author = NMediaUtils.AUTHOR,
                    published = NMediaUtils.PUBLISHED
                ) else post
            )
        )
//        post.video?.let {
//            dao.save(PostAndVideo.videoFromDto(it))
//        }
    }
    override fun likeById(id: Long) = dao.likeById(id)
    override fun shareById(id: Long) = dao.shareById(id)
    override fun removeById(id: Long) = dao.removeById(id)
}

