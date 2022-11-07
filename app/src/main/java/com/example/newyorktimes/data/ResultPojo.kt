package com.example.newyorktimes.data

import com.example.newyorktimes.domain.entity.Article
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticlePojo(
    @SerializedName("id")
    val articleID: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("published_date")
    val date: String,
    @SerializedName("section")
    val section: String,
    @SerializedName("")
    val byline: String,
    @SerializedName("media")
    val media: List<MediaPojo>,
    @SerializedName("abstract")
    val text: String
) : Serializable

class MediaPojo(
    @SerializedName("media-metadata")
    val mediaData: List<MediaDataPojo>
)

class MediaDataPojo(
    @SerializedName("url")
    val url: String
)


fun ArticlePojo.toArticle(): Article {
    return Article(articleID, title, date, section, byline, media, text)
}