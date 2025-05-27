package com.example.wildskillz.model.LearningContent

sealed class LearningContent(
    open val name: String,
    open val author: String,
    open val thumbnailResId: Int,
    open val price: String
) {
    data class Video(
        override val name: String,
        override val author: String,
        override val thumbnailResId: Int,
        override val price: String,
        val duration: String,
        val views: String
    ) : LearningContent(name, author, thumbnailResId, price)

    data class Workshop(
        override val name: String,
        override val author: String,
        override val thumbnailResId: Int,
        override val price: String,
        val time: String,
        val location: String,
        val capacity: Int
    ) : LearningContent(name, author, thumbnailResId, price)
}

