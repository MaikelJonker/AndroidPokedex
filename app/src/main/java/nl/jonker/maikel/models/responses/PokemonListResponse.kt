package nl.jonker.maikel.models.responses

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResponse>,
)

