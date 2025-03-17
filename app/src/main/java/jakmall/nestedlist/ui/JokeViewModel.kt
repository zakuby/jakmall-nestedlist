package jakmall.nestedlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakmall.nestedlist.data.JokeRepository
import jakmall.nestedlist.data.model.JokeCategory
import jakmall.nestedlist.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val repository: JokeRepository
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            repository.fetchJokeCategory()
        }
    }

    val screenState: StateFlow<UiState<JokeCategory>> = repository.observeJokeCategory()
        .combine(_isRefreshing) { items, isRefreshing ->
            UiState(items = items, isRefreshing = isRefreshing)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState()
        )

    fun onPullToRefreshTrigger() {
        _isRefreshing.update { true }
        viewModelScope.launch {
            repository.fetchJokeCategory()
            _isRefreshing.update { false }
        }
    }

    suspend fun fetchJokeFromCategory(category: String, amount: Int = 1) = repository.fetchJokes(category, amount)

}