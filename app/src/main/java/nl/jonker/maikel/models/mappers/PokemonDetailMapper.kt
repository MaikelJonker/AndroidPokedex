package nl.jonker.maikel.models.mappers

import nl.jonker.maikel.models.DetailedPokemon
import nl.jonker.maikel.models.Stats
import nl.jonker.maikel.models.responses.PokemonDetailResponse
import javax.inject.Inject

class PokemonDetailMapper @Inject constructor(){
    fun mapDetailedPokemon(apiResponse: PokemonDetailResponse): DetailedPokemon {
        val stats = apiResponse.stats.fold(Stats(0, 0, 0, 0, 0, 0)) { acc, stat ->
            when (stat.stat.name) {
                "hp" -> acc.copy(hp = stat.base_stat)
                "attack" -> acc.copy(attack = stat.base_stat)
                "defense" -> acc.copy(defense = stat.base_stat)
                "special-attack" -> acc.copy(specialAttack = stat.base_stat)
                "special-defense" -> acc.copy(specialDefense = stat.base_stat)
                "speed" -> acc.copy(speed = stat.base_stat)
                else -> acc
            }
        }

        return DetailedPokemon(
            id = apiResponse.id,
            name = apiResponse.name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${apiResponse.id}.png",
            baseExperience = apiResponse.base_experience,
            weight = apiResponse.weight,
            height = apiResponse.height,
            types = apiResponse.types.map { it.type.name },
            abilities = apiResponse.abilities.map { it.ability.name },
            stats = stats
        )
    }
}