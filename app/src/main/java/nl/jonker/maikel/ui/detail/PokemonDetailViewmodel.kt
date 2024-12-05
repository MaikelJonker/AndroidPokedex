package nl.jonker.maikel.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import nl.jonker.maikel.models.DetailedPokemon
import nl.jonker.maikel.models.mappers.PokemonDetailMapper
import nl.jonker.maikel.repositories.FavouritesRepository
import nl.jonker.maikel.services.PokemonService
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val mapper: PokemonDetailMapper,
    private val pokemonService: PokemonService,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {
    private val _pokemonDetail = MutableStateFlow<DetailedPokemon?>(null)
    val pokemonDetail: StateFlow<DetailedPokemon?> = _pokemonDetail

    private val _isFavourite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavourite

    fun getPokemon(id: Int) {
        viewModelScope.launch {
            try{
                val detail = pokemonService.getPokemonById(id)
                _pokemonDetail.value = mapper.mapDetailedPokemon(detail)
            } catch (e: Exception) {
                _pokemonDetail.value = null
            }
        }
    }

    fun checkIfFavorite(pokemonId: String) {
        viewModelScope.launch {
            val favorites = favouritesRepository.favouritesFlow.first()
            _isFavourite.value = favorites.contains(pokemonId)
        }
    }

    fun toggleFavourite(pokemonId: String) {
        viewModelScope.launch {
            favouritesRepository.toggleFavourite(pokemonId)
            _isFavourite.value = !_isFavourite.value
        }
    }
}