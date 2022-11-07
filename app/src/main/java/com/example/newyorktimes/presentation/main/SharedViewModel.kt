package com.example.newyorktimes.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newyorktimes.domain.entity.Article
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val _onItemClickStateFlow: MutableSharedFlow<Article?> = MutableSharedFlow()
    val onItemClickStateFlow = _onItemClickStateFlow.asSharedFlow()

    fun performItemClick(item: Article) {
        viewModelScope.launch {
            _onItemClickStateFlow.emit(item)
        }
    }
}