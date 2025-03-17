package jakmall.nestedlist.data.remote.model

import jakmall.nestedlist.data.model.JokeCategory

data class JokeCategoryResponse(
    val categories: List<String>?,
    val categoryAliases: List<JokeCategoryAliasesResponse>?
) : BaseResponse()

data class JokeCategoryAliasesResponse(
    val alias: String,
    val resolved: String
)

fun JokeCategoryResponse.mapResponseToJokeCategory(): List<JokeCategory> =
    if (categories.isNullOrEmpty())
        emptyList()
    else
        categories.map { category ->
            JokeCategory(
                category = category,
                aliases = categoryAliases?.firstOrNull {
                    it.resolved == category
                }?.alias?.ifEmpty { category } ?: category
            )
        }