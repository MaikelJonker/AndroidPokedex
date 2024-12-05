package nl.jonker.maikel.ui.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.jonker.maikel.services.PokemonService
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.jonker.maikel.models.Pokemon
import nl.jonker.maikel.models.mappers.PokemonFavouritesMapper
import nl.jonker.maikel.repositories.FavouritesRepository
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val mapper: PokemonFavouritesMapper,
    private val pokemonService: PokemonService,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _favourites = MutableStateFlow<Set<String>>(emptySet())
    private val _favouritePokemon = MutableStateFlow<Set<Pokemon>>(emptySet())
    val favorites: StateFlow<Set<Pokemon>> = _favouritePokemon

    init {
        viewModelScope.launch {
            favouritesRepository.favouritesFlow.collect { newFavorites ->
                _favourites.value = newFavorites
                fetchFavoritePokemonData(newFavorites)
            }
        }
    }

    private suspend fun fetchFavoritePokemonData(favoriteIds: Set<String>) {
        val pokemonList = mutableListOf<Pokemon>()
        for (id in favoriteIds) {
            try {
                val response = pokemonService.getPokemonById(id.toInt())
                val pokemon = mapper.mapFavouritePokemon(response)
                pokemonList.add(Pokemon(pokemon.id, pokemon.name, pokemon.imageUrl))
            } catch (e: Exception) {
                Log.e("FavouritesViewModel", "Error fetching Pokémon with ID: $id", e)
            }
        }
        _favouritePokemon.value = pokemonList.toSet()
    }
}