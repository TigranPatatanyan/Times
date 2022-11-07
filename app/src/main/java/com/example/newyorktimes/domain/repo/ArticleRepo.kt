package com.example.newyorktimes.domain.repo

import com.example.newyorktimes.data.Period
import com.example.newyorktimes.domain.entity.Article

interface ArticleRepo {
    suspend fun getArticles(period: Period): List<Article>
}