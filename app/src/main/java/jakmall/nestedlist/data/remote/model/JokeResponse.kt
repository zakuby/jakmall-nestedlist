package jakmall.nestedlist.data.remote.model

import jakmall.nestedlist.data.model.Joke

data class JokeResponse(
    val jokes: List<Joke>?
) : BaseResponse()