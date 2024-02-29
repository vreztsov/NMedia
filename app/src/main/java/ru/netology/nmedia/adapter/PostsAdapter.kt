package ru.netology.nmedia.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.NMediaUtils

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onEdit(post: Post)
    fun onRemove(post: Post)
    fun onShare(post: Post)
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) :
    ListAdapter<Post, PostViewHolder>(PostDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: OnInteractionListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private fun doIntentToPlayURL(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        ContextCompat.startActivity(context, intent, null)
    }

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.text = NMediaUtils.numFormat(post.likes)
            share.text = NMediaUtils.numFormat(post.shares)
            view.text = NMediaUtils.numFormat(post.views)
            avatar.setImageResource(R.drawable.ic_netology_original_48dp)
            like.isChecked = post.likedByMe

            layoutVideo.visibility = View.GONE
            if (post.video != null) {
                layoutVideo.visibility = View.VISIBLE
                txtName.text = post.video.name
                txtViewCount.text = post.video.views.toString()
            }
            //----------------------------------------------------

            playVideo.setOnClickListener {
                if (post.video != null) {
                    doIntentToPlayURL(it.context, post.video.url)
                }
            }
            //----------------------------------------------------

            imageVideo.setOnClickListener {
                if (post.video != null) {
                    doIntentToPlayURL(it.context, post.video.url)
                }
            }


            like.setOnClickListener {
                listener.onLike(post)
            }
            share.setOnClickListener {
                listener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}