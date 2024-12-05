package nl.jonker.maikel.ui.pokedex

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import nl.jonker.maikel.ui.cells.PokemonCell
import nl.jonker.maikel.ui.error.ErrorPage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Pokedex(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PokedexViewModel = hiltViewModel(),
    toErrorPage: () -> Unit
) {
    val state = viewModel.state.collectAsLazyPagingItems()
    val searchState = viewModel.searchResults.collectAsState()
    val searchQuery by viewModel.searchState.collectAsState()
    val connectionErrorState by viewModel.connectionErrorState.collectAsState()

    if(!connectionErrorState) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            SearchBar(
                viewModel = viewModel,
                searchQuery = searchQuery
            )
            Text(
                text = "All Pokemon",
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.titleLarge
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                modifier = Modifier.fillMaxSize()
            ) {
//Pager is used for regular scrolling, only once search is used it will collect all pokemon at once
                if (searchQuery.isEmpty()) {
                    if(state.itemCount == 0){
                        viewModel.retry()
                    }
                    else {
                        items(state.itemCount) { index ->
                            state[index]?.let { pokemon ->
                                PokemonCell(pokemon, onNavigateToDetail)
                            }
                        }
                    }
                } else {
                    items(searchState.value) { pokemon ->
                        PokemonCell(pokemon, onNavigateToDetail)
                    }
                }
            }
        }
    }
    else{
        toErrorPage()
    }
}

@Composable
fun SearchBar(
    viewModel: PokedexViewModel,
    searchQuery: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .graphicsLayer { shadowElevation = 8.dp.toPx() }
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { newQuery ->
                viewModel.updateSearchQuery(newQuery)
            },
            placeholder = {
                Text(
                    text = "Search",
                    fontSize = 16.sp,
                    color = Color(0xFFBDBDBD)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = (Icons.Filled.Search),
                    contentDescription = "Search",
                    tint = Color.Black
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            )
        )
    }
}
