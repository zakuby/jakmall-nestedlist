package jakmall.nestedlist.data.remote

import jakmall.nestedlist.data.model.Joke
import jakmall.nestedlist.data.model.JokeCategory
import jakmall.nestedlist.data.remote.model.NetworkResponse
import jakmall.nestedlist.data.remote.model.mapResponseToJokeCategory
import javax.inject.Inject

class JokeRemoteDataSourceImpl @Inject constructor(
    private val jokeService: JokeService
) : JokeRemoteDataSource {
    override suspend fun getJokes(category: String): NetworkResponse<List<Joke>> {
        runCatching {
            jokeService.getJokes(category = category)
        }.onSuccess { response ->
            return if (response.error)
                NetworkResponse.NetworkError
            else
                NetworkResponse.NetworkSuccess(data = response.jokes ?: emptyList())
        }.onFailure {
            return NetworkResponse.NetworkError
        }

        return NetworkResponse.NetworkError
    }

    override suspend fun getJokeCategories(): NetworkResponse<List<JokeCategory>> {
        runCatching {
            jokeService.getCategories()
        }.onSuccess { response ->
            return if (response.error)
                NetworkResponse.NetworkError
            else {
                NetworkResponse.NetworkSuccess(data = response.mapResponseToJokeCategory())
            }
        }.onFailure {
            return NetworkResponse.NetworkError
        }

        return NetworkResponse.NetworkError
    }
}