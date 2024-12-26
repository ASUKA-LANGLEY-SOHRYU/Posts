package com.example.posts.presenter.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.example.posts.R
import com.example.posts.data.provider.GlideUrlProvider
import com.example.posts.databinding.PostBinding
import com.example.posts.presenter.model.post.Post

class PostsAdapter(
    private val glideUrlProvider: GlideUrlProvider
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var posts: MutableList<Post> = mutableListOf()
    private lateinit var onButtonClickListener: OnButtonClickListener
    private lateinit var onScrolledToTheEndListener: OnScrolledToTheEndListener

    @SuppressLint("NotifyDataSetChanged")
    fun addPosts(value: Post){
        posts.add(value)
        notifyItemChanged(posts.size - 1)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPosts(value: MutableList<Post>){
        this.posts = value
        notifyItemChanged(0)
        notifyDataSetChanged()
    }

    interface OnButtonClickListener {
        fun onButtonClick(position: Int)
    }

    fun setOnButtonClickListener(listener: OnButtonClickListener){
        onButtonClickListener = listener
    }

    interface OnScrolledToTheEndListener {
        fun onScrolledToTheEnd();
    }

    fun setOnScrolledToTheEndListener(listener: OnScrolledToTheEndListener) {
        onScrolledToTheEndListener = listener
    }

    inner class PostsViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = PostBinding.bind(view)


        fun bind(post: Post){
            binding.apply {
                Log.i("ABCABC", "gg" + post.toString())
                description.text = post.description
                Log.i("ABCABC",description.text.toString())

                Glide.with(itemView.context)
                    .load(glideUrlProvider.provide(post.imageUrl))
                    .signature(ObjectKey(post.imageUrl.hashCode().toString()))
                    //.transform(RoundedCorners(10))
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.progress_bar_default)
                    .into(image)

                likesImage.setOnClickListener {
                    likesImage.setImageResource(R.drawable.like_on)//TODO
                    likes.text = "${likes.text.toString().toInt() + 1}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        Log.i("ABCABC", "abf" + position.toString())
        holder.bind(posts[position])
        if (::onScrolledToTheEndListener.isInitialized && position == itemCount - 1)
            onScrolledToTheEndListener.onScrolledToTheEnd()
    }
}