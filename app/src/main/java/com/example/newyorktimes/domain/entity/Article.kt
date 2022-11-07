package com.example.newyorktimes.domain.entity

import com.example.newyorktimes.data.MediaPojo
import java.io.Serializable

data class Article(
    val articleID: String?,
    val title: String?,
    val date: String?,
    val section: String?,
    val byline: String?,
    val media: List<MediaPojo>?,
    val text: String?
) : Serializable