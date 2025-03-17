package jakmall.nestedlist.data.model

data class Joke(
    val category: String,
    val type: String,
    val joke: String,
    val flags: JokeFlag,
    val id: Int,
    val safe: Boolean,
    val lang: String
){
    data class JokeFlag(
        val nsfw: Boolean,
        val religious: Boolean,
        val political: Boolean,
        val sexist: Boolean,
        val explicit: Boolean
    )
}
