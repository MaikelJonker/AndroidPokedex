package nl.jonker.maikel.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import nl.jonker.maikel.models.Pokemon

@Composable
fun PokemonCell(pokemon: Pokemon, onNavigateToDetail: (Int) -> Unit){
    Box(
        modifier = Modifier.padding(8.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 10.dp))
            .clickable(onClick = { onNavigateToDetail(pokemon.id) })
    ) {
        Column {
            Box(
                modifier = Modifier.padding(10.dp)
                    .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(size = 5.dp)
                    )
            ) {
                Text(
                    text = String.format("%03d", pokemon.id),
                    modifier = Modifier.padding(5.dp),
                    color = Color.White
                )
            }
            SubcomposeAsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
                    .size(150.dp, 150.dp),
                loading = {
                    Box(Modifier.size(30.dp)
                        .fillMaxSize()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            )
            Text(
                text = pokemon.name.replaceFirstChar {it.titlecase()},
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                    .padding(10.dp)
            )
        }
    }
}