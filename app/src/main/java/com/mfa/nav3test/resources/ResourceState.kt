package com.mfa.nav3test.resources

open class ResourceState<out T> {
    data object Idle : ResourceState<Nothing>()
    data object Loading : ResourceState<Nothing>()
    data class Error(val exception: String) : ResourceState<Nothing>()
    data class Success<out T>(val data: T) : ResourceState<T>()
}
