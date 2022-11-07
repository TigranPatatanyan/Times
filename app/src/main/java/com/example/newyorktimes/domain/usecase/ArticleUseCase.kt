package com.example.newyorktimes.domain.usecase

import com.example.newyorktimes.core.domain.BaseUseCase
import com.example.newyorktimes.core.domain.Result
import com.example.newyorktimes.data.Period
import com.example.newyorktimes.domain.entity.Article
import com.example.newyorktimes.domain.repo.ArticleRepo

interface ArticleUseCase {
    suspend fun getArticle(period: Period): Result<List<Article>>
}

class ArticleUseCaseImpl(private val articleRepo: ArticleRepo) : ArticleUseCase, BaseUseCase() {
    override suspend fun getArticle(period: Period): Result<List<Article>> = safeCall {
        articleRepo.getArticles(period = period)
    }

}