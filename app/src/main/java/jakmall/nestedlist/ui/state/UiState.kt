package jakmall.nestedlist.ui.state

data class UiState<T>(
    val items: List<T> = listOf(),
    val isRefreshing: Boolean = false
)