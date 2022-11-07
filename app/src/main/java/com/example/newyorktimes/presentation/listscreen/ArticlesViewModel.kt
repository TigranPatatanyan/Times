package com.example.newyorktimes.presentation.listscreen

import android.os.Bundle
import androidx.lifecycle.*
import com.example.newyorktimes.core.presenter.BaseViewModel
import com.example.newyorktimes.data.Period
import com.example.newyorktimes.domain.entity.Article
import com.example.newyorktimes.domain.usecase.ArticleUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticlesViewModel(private val articleUseCase: ArticleUseCase) : BaseViewModel() {
    private val _articlesStateFlow: MutableStateFlow<List<Article>?> = MutableStateFlow(null)
    val articlesStateFlow = _articlesStateFlow.asStateFlow()


    fun onSaveState(bundle: Bundle) {
        viewModelScope.launch(Dispatchers.IO) {
            _articlesStateFlow.collect {
                it?.forEachIndexed { index, item ->
                    bundle.putSerializable("$ARTICLES$index", item)
                }
                it?.let { bundle.putInt(LAST_INDEX, it.size - 1) }
            }
        }
    }

    fun onRestoreState(bundle: Bundle) {
        viewModelScope.launch(Dispatchers.Main) {
            bundle.getInt(LAST_INDEX, -1).takeIf { it > -1 }?.run {
                val articles: ArrayList<Article> = arrayListOf()
                for (i in 0..this) {
                    (bundle.getSerializable("$ARTICLES$i") as? Article)?.let { articles.add(it) }
                }
                _articlesStateFlow.emit(articles)
            }
        }
    }

    fun tryToDownloadArticleList(period: Period) {
        viewModelScope.launch(Dispatchers.IO) {
            articleUseCase.getArticle(period).resultDefaultHandle {
                _articlesStateFlow.emit(it)
            }
        }
    }


    companion object {
        const val ARTICLES = "articlesList"
        const val LAST_INDEX = "lastIndex"
    }
}