package jakmall.nestedlist.data

import jakmall.nestedlist.data.model.Joke
import jakmall.nestedlist.data.model.JokeCategory
import kotlinx.coroutines.flow.Flow

interface JokeRepository {

    suspend fun fetchJokes(category: String, amount: Int): List<Joke>

    fun observeJokeCategory(): Flow<List<JokeCategory>>

    suspend fun fetchJokeCategory()
}