package nl.jonker.maikel.services

import nl.jonker.maikel.models.responses.PokemonDetailResponse
import nl.jonker.maikel.models.responses.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("v2/pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 1400,
        @Query("offset") offset: Int = 0,
    ): PokemonListResponse

    @GET("v2/pokemon/{id}")
    suspend fun getPokemonById(
        @Path("id") pokemonId: Int
    ): PokemonDetailResponse
}
