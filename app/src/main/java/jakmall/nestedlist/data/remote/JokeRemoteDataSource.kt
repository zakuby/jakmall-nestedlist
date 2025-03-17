package jakmall.nestedlist.data.remote

import jakmall.nestedlist.data.model.Joke
import jakmall.nestedlist.data.model.JokeCategory
import jakmall.nestedlist.data.remote.model.NetworkResponse

interface JokeRemoteDataSource {
    suspend fun getJokes(category: String): NetworkResponse<List<Joke>>
    suspend fun getJokeCategories(): NetworkResponse<List<JokeCategory>>
}