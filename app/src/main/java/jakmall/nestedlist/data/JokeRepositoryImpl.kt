package jakmall.nestedlist.data

import jakmall.nestedlist.data.model.Joke
import jakmall.nestedlist.data.model.JokeCategory
import jakmall.nestedlist.data.remote.JokeRemoteDataSource
import jakmall.nestedlist.data.remote.model.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class JokeRepositoryImpl(
    private val jokeRemoteDataSource: JokeRemoteDataSource
) : JokeRepository {

    private val _isError = MutableStateFlow(false)

    private val _jokeCategory = MutableStateFlow<List<JokeCategory>>(emptyList())

    override suspend fun fetchJokes(category: String, amount: Int): List<Joke> {
        runCatching {
            jokeRemoteDataSource.getJokes(category)
        }.onSuccess { fetchJokes ->
            return when (fetchJokes){
                NetworkResponse.NetworkError -> emptyList()
                is NetworkResponse.NetworkSuccess -> fetchJokes.data.take(amount)
            }
        }.onFailure {
            return emptyList()
        }

        return emptyList()
    }

    override fun observeJokeCategory(): Flow<List<JokeCategory>> = _jokeCategory

    override suspend fun fetchJokeCategory() {
        _jokeCategory.value = emptyList()
        withContext(Dispatchers.IO) {
            when (val fetchJokeCategories = jokeRemoteDataSource.getJokeCategories()){
                NetworkResponse.NetworkError -> _isError.value = true
                is NetworkResponse.NetworkSuccess -> {
                    _isError.value = false
                    _jokeCategory.value = fetchJokeCategories.data
                }
            }
        }
    }
}