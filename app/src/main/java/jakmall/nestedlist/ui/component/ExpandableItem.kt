package jakmall.nestedlist.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import jakmall.nestedlist.data.model.Joke
import jakmall.nestedlist.data.model.JokeCategory
import jakmall.nestedlist.ui.JokeViewModel
import jakmall.nestedlist.ui.theme.Purple40
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ExpandableItem(
    index: Int,
    item: JokeCategory,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onScrollToTop: () -> Unit,
    viewModel: JokeViewModel
) {
    var jokes by remember { mutableStateOf(listOf<Joke>()) }
    var showDialog by remember { mutableStateOf(false) }
    var jokeDialog by remember { mutableStateOf("") }

    LaunchedEffect(item.category) {
        viewModel.viewModelScope.launch {
            jokes = viewModel.fetchJokeFromCategory(category = item.category, amount = 2)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = index.toString(), modifier = Modifier.padding(end = 8.dp))
                    Text(text = item.aliases)
                }

                Button(onClick = onScrollToTop, enabled = index > 0) {
                    Text(if (index > 0) "Scroll to Top" else "Top")
                }
                IconButton(onClick = onToggle) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    jokes.forEach { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 0.5.dp,
                                    shape = RoundedCornerShape(8.dp),
                                    color = Purple40
                                )
                                .padding(10.dp)
                                .clickable {
                                    showDialog = true
                                    jokeDialog = item.joke
                                }
                        ) {
                            Text(
                                text = item.joke,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                    if (jokes.size < 4) {
                        Button(
                            onClick = {
                                viewModel.viewModelScope.launch(Dispatchers.IO) {
                                    val newJokes =
                                        viewModel.fetchJokeFromCategory(item.category, amount = 1)
                                    jokes = jokes.toMutableList().apply { addAll(newJokes) }
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            enabled = jokes.size < 4
                        ) {
                            Text("Add more data")
                        }
                    }

                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Joke Details") },
            text = { Text(jokeDialog) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}