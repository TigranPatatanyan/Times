package com.example.newyorktimes.core.domain

interface BaseError {
    val extraErrorCode: Int
    val message: String?
}

open class BaseException(override val extraErrorCode: Int, message: String? = null) :
    Exception(message),
    BaseError


const val NO_INTERNET: Int = 666

const val UNREGISTERED_EXCEPTION = 777

