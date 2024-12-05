package nl.jonker.maikel.ui.detail

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import nl.jonker.maikel.ui.cells.PokemonStatsCell
import nl.jonker.maikel.ui.cells.TypeBadge

@Composable
fun DetailPage(
    pokemonId: Int,
    viewModel: PokemonDetailViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(pokemonId) {
        viewModel.getPokemon(pokemonId)
        viewModel.checkIfFavorite(pokemonId.toString())
    }

    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(200)
        for(i in 1..2) {
            offsetY.animateTo(
                targetValue = -20f,
                animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
            )
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
            )
        }
    }

    val pokemonDetail = viewModel.pokemonDetail.collectAsState().value
    val isFavorite by viewModel.isFavorite.collectAsState()
    val scrollState = rememberScrollState()

    // Use a main Box to set up the two-tone background
    Box(modifier = Modifier.fillMaxSize()
        .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(MaterialTheme.colorScheme.background)
        )

        pokemonDetail?.let { detail ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 8.dp)
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Row(modifier = Modifier.clickable { onBack() }.padding(top = 10.dp)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                        Text(
                            text = "Vorige",
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { viewModel.toggleFavourite(pokemonId.toString()) }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) Color.Red else Color.Black
                        )
                    }
                }
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Text(
                        text = detail.name.replaceFirstChar { it.titlecase() },
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = String.format("#%03d", detail.id),
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                }
                Row {
                    detail.types.forEach { type ->
                        TypeBadge(type)
                    }
                }
                AsyncImage(
                    model = detail.imageUrl,
                    contentDescription = detail.name,
                    alignment = Alignment.Center,
                    modifier = Modifier.height(250.dp)
                        .fillMaxWidth()
                        .offset(y = offsetY.value.dp)
                )
                PokemonStatsCell(detail)
            }
        }
        ?: run {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}




