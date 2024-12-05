package nl.jonker.maikel.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "pokemon_favourites")

class FavouritesRepository(private val context: Context) {

    private val FAVOURITES_KEY = stringSetPreferencesKey("favourite_pokemon_ids")

    val favouritesFlow: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVOURITES_KEY] ?: emptySet()
        }

    suspend fun toggleFavourite(pokemonId: String) {
        context.dataStore.edit { preferences ->
            val currentFavourites = preferences[FAVOURITES_KEY] ?: emptySet()
            if (currentFavourites.contains(pokemonId)) {
                preferences[FAVOURITES_KEY] = currentFavourites - pokemonId
            } else {
                preferences[FAVOURITES_KEY] = currentFavourites + pokemonId
            }
        }
    }
}