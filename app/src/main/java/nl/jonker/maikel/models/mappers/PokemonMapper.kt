package nl.jonker.maikel.models.mappers

import nl.jonker.maikel.models.Pokemon
import nl.jonker.maikel.models.responses.PokemonResponse
import javax.inject.Inject

class PokemonMapper @Inject constructor() {
    fun mapPokemon(response: PokemonResponse): Pokemon {
        val id = response.url.split("/").dropLast(1).last().toInt()
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        return Pokemon(
            id = id,
            name = response.name,
            imageUrl = imageUrl
        )
    }
}
