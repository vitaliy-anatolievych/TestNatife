package com.testtask.testnatife.core.type

open class HandleOnce<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Синглтончик для контента, позволяет юзать повторно, а не подгружать.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}