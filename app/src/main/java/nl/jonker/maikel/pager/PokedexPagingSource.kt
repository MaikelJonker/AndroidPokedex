package nl.jonker.maikel.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nl.jonker.maikel.models.responses.PokemonResponse
import nl.jonker.maikel.services.PokemonService

class PokedexPagingSource(
    private val service: PokemonService
) : PagingSource<Int, PokemonResponse>() {
    companion object Constants{
        const val PAGE_SIZE = 20
    }
    // Refresh key determines which set of data should be fetched when the source is invalidated
    override fun getRefreshKey(state: PagingState<Int, PokemonResponse>): Int? {
        // Determine the offset based on the current position in the data set
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_SIZE)
        }
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResponse> {
        val offset = params.key ?: 0 // If no offset has been set, take 0
        return try {
            val response = service.getPokemonList(offset = offset, limit = PAGE_SIZE)
            LoadResult.Page(
                data = response.results,
                prevKey = if (response.previous != null) offset - PAGE_SIZE else null, // Safe set previous offset value
                nextKey = if (response.next != null) offset + PAGE_SIZE else null // Safe set next offset value
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }
}