package com.aaidhah.nadeem.utils.viewstate

import com.aaidhah.nadeem.model.task.Task

sealed class SingleViewState {
    // Represents different states for the Single Task Details Screen
    object Empty : SingleViewState()
    object Loading : SingleViewState()
    data class Success(val task: Task) : SingleViewState()
    data class Error(val exception: Throwable) : SingleViewState()
}