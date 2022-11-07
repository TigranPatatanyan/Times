package com.example.newyorktimes.core.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseUseCase {
    suspend fun <T : Any?> safeCall(call: suspend CoroutineScope.() -> T): Result<T> = try {
        coroutineScope {
            Result.Success(call())
        }
    } catch (connectionException: ConnectException) {
        Result.Error(connectionException, NO_INTERNET)
    } catch (connectionException: SocketTimeoutException) {
        Result.Error(connectionException, NO_INTERNET)
    } catch (connectionException: UnknownHostException) {
        Result.Error(connectionException, NO_INTERNET)
    } catch (baseException: BaseException) {
        Result.Error(baseException, baseException.extraErrorCode)
    } catch (ex: Exception) {
        Result.Error(ex, UNREGISTERED_EXCEPTION)
    }
}