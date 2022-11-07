package com.example.newyorktimes.data

import com.example.newyorktimes.core.data.handleResult
import com.example.newyorktimes.domain.entity.Article
import com.example.newyorktimes.domain.repo.ArticleRepo

class OnlineRequestsRepo(private val requestAPI: RequestAPI) : ArticleRepo {
    override suspend fun getArticles(period: Period): List<Article> {
        val result = requestAPI.getMostPopularNews(period.period.toString()).handleResult()
        return result?.map { it.toArticle() } ?: listOf()
    }
}

enum class Period(val period: Int) {
    DAY(1),
    WEEK(7),
    MONTH(30)
}