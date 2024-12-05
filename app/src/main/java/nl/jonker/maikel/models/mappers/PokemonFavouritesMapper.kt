package nl.jonker.maikel.models.mappers

import nl.jonker.maikel.models.Pokemon
import nl.jonker.maikel.models.responses.PokemonDetailResponse
import javax.inject.Inject

class PokemonFavouritesMapper @Inject constructor(){
    fun mapFavouritePokemon(response: PokemonDetailResponse): Pokemon {
        val id = response.id
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        return Pokemon(
            id = id,
            name = response.name,
            imageUrl = imageUrl
        )
    }
}