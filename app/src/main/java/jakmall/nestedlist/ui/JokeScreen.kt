package jakmall.nestedlist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jakmall.nestedlist.R
import jakmall.nestedlist.data.model.JokeCategory
import jakmall.nestedlist.ui.component.ExpandableItem
import jakmall.nestedlist.ui.component.PullToRefreshBox
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(viewModel: JokeViewModel){
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = viewModel::onPullToRefreshTrigger,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            ExpandableLazyColumn(state.items, viewModel)
        }
    }
}


@Composable
fun ExpandableLazyColumn(
    items: List<JokeCategory>,
    viewModel: JokeViewModel
) {
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(items) {
        expandedStates.clear()
    }

    Column {
        LazyColumn(state = listState) {
            itemsIndexed(items) { index, item ->
                ExpandableItem(
                    index = index,
                    item = item,
                    isExpanded = expandedStates[index] ?: false,
                    onToggle = { expandedStates[index] = !expandedStates.getOrDefault(index, false) },
                    onScrollToTop = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                            expandedStates.keys.forEach { expandedStates[it] = false }
                        }
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}