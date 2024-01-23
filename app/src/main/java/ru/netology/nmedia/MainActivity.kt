package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.NMediaUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likeCounter.text = NMediaUtils.numFormat(post.likes)
                shareCounter.text = NMediaUtils.numFormat(post.shares)
                viewCounter.text = NMediaUtils.numFormat(post.views)
                avatar.setImageResource(R.drawable.ic_netology_original_48dp)
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
            }
            binding.like.setOnClickListener {
                viewModel.like()
            }
            binding.share.setOnClickListener {
                viewModel.share()
            }
        }
    }
}