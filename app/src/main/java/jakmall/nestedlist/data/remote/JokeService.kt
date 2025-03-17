package jakmall.nestedlist.data.remote

import jakmall.nestedlist.data.remote.model.JokeCategoryResponse
import jakmall.nestedlist.data.remote.model.JokeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeService {
    @GET("categories")
    suspend fun getCategories(): JokeCategoryResponse

    @GET("joke/{category}")
    suspend fun getJokes(
        @Path("category") category: String,
        @Query("type") type: String = "single",
        @Query("amount") amount: Int = 2,
        @Query("blacklistFlags") flags: String = "nsfw"
    ): JokeResponse

    companion object {
        const val BASE_URL = "https://v2.jokeapi.dev/"
    }
}