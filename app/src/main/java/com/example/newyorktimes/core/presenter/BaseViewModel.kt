package com.example.newyorktimes.core.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newyorktimes.core.domain.NO_INTERNET
import com.example.newyorktimes.core.domain.Result
import com.example.newyorktimes.core.domain.UNREGISTERED_EXCEPTION
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val errorMap: Map<Int, () -> Unit> by lazy { initErrorMap() }
    open fun getErrorActionsMap(): Map<Int, () -> Unit> = mutableMapOf()

    private val _noInternetConnection: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    private val _somethingWentWrong: MutableStateFlow<Boolean?> = MutableStateFlow(null)

    val noInternetConnection = _noInternetConnection.asStateFlow()
    val somethingWentWrong = _somethingWentWrong.asStateFlow()

    private fun initErrorMap(): Map<Int, () -> Unit> = getErrorActionsMap().toMutableMap().apply {
        putIfAbsent(NO_INTERNET) {
            onNoInternetConnection()
        }
        putIfAbsent(UNREGISTERED_EXCEPTION) {
            onSomethingWentWrong()
        }
    }


    private fun handleError(errorId: Int) {
        errorMap[errorId]?.invoke()
    }

    private fun onNoInternetConnection() {
        viewModelScope.launch {
            _noInternetConnection.emit(true)
        }
    }

    private fun onSomethingWentWrong() {
        viewModelScope.launch {
            _somethingWentWrong.emit(true)

        }
    }

    suspend fun <T> Result<T>.resultDefaultHandle(successBlock: suspend (T?) -> Unit) {
        when (this) {
            is Result.Success ->
                successBlock(data)
            is Result.Error -> {
                handleError(extraErrorCode)
                Log.e("Error", exception.stackTraceToString())
            }
        }
    }
}