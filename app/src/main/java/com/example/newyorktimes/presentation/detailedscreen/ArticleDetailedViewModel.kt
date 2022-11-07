package com.example.newyorktimes.presentation.detailedscreen

import androidx.lifecycle.SavedStateHandle
import com.example.newyorktimes.core.presenter.BaseViewModel
import com.example.newyorktimes.presentation.detailedscreen.ArticleDetailedFragment.Companion.DETAILED_FRAGMENT_ARGS
import com.example.newyorktimes.domain.entity.Article

class ArticleDetailedViewModel(private val savedStateHandle: SavedStateHandle) : BaseViewModel() {
    var articleData: Article? = null
        get() = savedStateHandle.get<Article>(DETAILED_FRAGMENT_ARGS)
        set(value) {
            savedStateHandle[DETAILED_FRAGMENT_ARGS] = value
            field = value
        }
}