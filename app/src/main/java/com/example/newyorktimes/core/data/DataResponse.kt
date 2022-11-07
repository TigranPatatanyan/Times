package com.example.newyorktimes.core.data

import com.example.newyorktimes.core.domain.BaseException
import com.example.newyorktimes.core.domain.UNREGISTERED_EXCEPTION
import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("status")
    val status: String = "OK",

    @SerializedName("copyrights")
    val copyrights: String? = null,

    @SerializedName("results")
    val data: T? = null
)

fun <T> DataResponse<T>.handleResult(): T? {
    if (status != "OK") {
        throw BaseException(UNREGISTERED_EXCEPTION, message = status)
    }
    return this.data
}