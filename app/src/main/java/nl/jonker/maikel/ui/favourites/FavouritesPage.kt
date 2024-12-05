package nl.jonker.maikel.ui.favourites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import nl.jonker.maikel.ui.cells.PokemonCell

@Composable
fun FavouritesPage(
    viewModel: FavouritesViewModel,
    onNavigateToDetail: (Int) -> Unit
){

    val favouritePokemon by viewModel.favorites.collectAsState()
    Column(modifier = Modifier.fillMaxSize()){
        Text(text = "Favourites",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(10.dp)
        )
        if (favouritePokemon.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(favouritePokemon.size){ index ->
                    PokemonCell(favouritePokemon.toList()[index], onNavigateToDetail)
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "You don't have any favourites yet",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


