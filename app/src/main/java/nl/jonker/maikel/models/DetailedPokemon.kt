package nl.jonker.maikel.models

data class DetailedPokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val baseExperience: Int,
    val weight: Int,
    val height: Int,
    val types: List<String>,
    val abilities: List<String>,
    val stats: Stats
)

data class Stats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)