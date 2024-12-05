package nl.jonker.maikel.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nl.jonker.maikel.models.Pokemon
import nl.jonker.maikel.models.mappers.PokemonMapper
import nl.jonker.maikel.pager.PokedexPagingSource
import nl.jonker.maikel.services.PokemonService
import javax.inject.Inject


@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val mapper: PokemonMapper,
    private val pokemonService: PokemonService
) : ViewModel() {

    val searchState = MutableStateFlow("")
    val connectionErrorState = MutableStateFlow(false)

    private val _searchResults = MutableStateFlow<List<Pokemon>>(emptyList())
    val searchResults: StateFlow<List<Pokemon>> = _searchResults

    private var allPokemonList: List<Pokemon> = emptyList()

    val state: Flow<PagingData<Pokemon>> = Pager(PagingConfig(pageSize = PokedexPagingSource.Constants.PAGE_SIZE)) {
        PokedexPagingSource(pokemonService)
    }.flow
        .map { data ->
            data.map(mapper::mapPokemon)
        }
        .cachedIn(viewModelScope)

    fun updateSearchQuery(query: String) {
        searchState.value = query
        searchPokemon(query)
    }

    fun searchPokemon(query: String) {
        viewModelScope.launch {
            try {
                if (allPokemonList.isNullOrEmpty()) {
                    allPokemonList = pokemonService.getPokemonList(limit = 2000, offset = 0)
                        .results.map(mapper::mapPokemon)
                }
                _searchResults.value =
                    allPokemonList.filter { pokemon -> pokemon.name.contains(query.lowercase()) }
            }
            catch(e: Exception){
                connectionErrorState.value = true;
            }
        }
    }

    fun retry() {
        searchPokemon(searchState.value)
    }
}
