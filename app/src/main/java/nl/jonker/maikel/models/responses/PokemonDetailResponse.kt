package nl.jonker.maikel.models.responses

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val height: Int,
    val weight: Int,
    val types: List<Type>,
    val abilities: List<Ability>,
    val stats: List<Stat>
)

data class Type(val type: TypeDetails)

data class TypeDetails(val name: String)

data class Ability(val ability: AbilityDetails)

data class AbilityDetails(val name: String)

data class Stat(val base_stat: Int, val stat: StatDetails)

data class StatDetails(val name: String)
