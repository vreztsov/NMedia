package ru.netology.nmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.util.TableInfo
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostVideo

@Entity
//    (
//    foreignKeys = [ForeignKey(
//        entity = PostVideoEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("videoId")
//    )]
//)
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val shares: Int = 0,
    val views: Int = 0,
//    @ColumnInfo(name = "video_id")
//    val videoId: Long? = null
) {
    fun toDto() = run {
        Post(
            id, author, content,
            published, likes, likedByMe,
            shares, views, null
        )
    }

    companion object {
        fun postFromDto(dto: Post) =
            with(dto) {
                PostEntity(
                    id, author, content,
                    published, likes, likedByMe,
                    shares, views
                )
            }
    }
}
//
//@Entity
//class PostVideoEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long,
//    val name: String = "",
//    val url: String = "",
//    val views: Int = 0
//)
//
//class PostAndVideo(
////    @Embedded
////    val video: PostVideoEntity?,
////    @Relation(entity = PostVideoEntity::class, parentColumn = "id", entityColumn = "video_id")
//    val post: PostEntity
//) {
//
////    fun toDto() = with(post) {
////        Post(
////            id, author, content,
////            published, likes, likedByMe,
////            shares, views, null
//////            video?.run {
//////                PostVideo(id, name, url, views)
//////            }
////        )
////    }
//
//    companion object {
//        fun postFromDto(dto: Post) =
//            PostEntity(
//                dto.id, dto.author, dto.content,
//                dto.published, dto.likes, dto.likedByMe,
//                dto.shares, dto.views
////                ,dto.video?.id
//            )
//
//        fun videoFromDto(dto: PostVideo) =
//            PostVideoEntity(
//                dto.id, dto.name, dto.url, dto.views
//            )
//    }
//}