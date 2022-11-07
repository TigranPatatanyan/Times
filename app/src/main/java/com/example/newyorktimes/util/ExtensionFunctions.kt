package com.example.newyorktimes.util

import android.widget.ImageView
import com.example.newyorktimes.domain.entity.Article
import com.squareup.picasso.Picasso

const val DEFAULT_URL = "https://cdn-icons-png.flaticon.com/512/14/14415.png"

fun ImageView.loadImage(data: Article?, size: Size) {
    val url =
        data?.media?.takeIf { it.isNotEmpty() && it[0].mediaData.isNotEmpty() }
            ?.run {
                this[0].mediaData[size.size].url
            } ?: DEFAULT_URL
    Picasso.get().load(url).into(this)
}

enum class Size(val size: Int) {
    SMALL(0),
    MEDIUM(1),
    LARGE(2)
}