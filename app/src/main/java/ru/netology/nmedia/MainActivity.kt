package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.NMediaUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 10

        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likeCounter.text = NMediaUtils.numFormat(post.likes)
            shareCounter.text = NMediaUtils.numFormat(post.shares)
            viewCounter.text = NMediaUtils.numFormat(post.views)

            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_liked_24)
            }

            like.setOnClickListener {
                if (post.likedByMe) post.likes-- else post.likes++
                post.likedByMe = !post.likedByMe
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24)
                likeCounter.text = NMediaUtils.numFormat(post.likes)
            }

            share.setOnClickListener {
                post.shares++
                shareCounter.text = NMediaUtils.numFormat(post.shares)
            }
        }
    }
}


//        println(R.string.hello) // число
//        println(getString(R.string.hello)) // "Привет, Мир!" или "Hello World!"
