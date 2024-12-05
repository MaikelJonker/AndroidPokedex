package nl.jonker.maikel.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


val TypeColors = mapOf(
    "Grass" to Color(0xFF78C850),
    "Poison" to Color(0xFFA040A0),
    "Fire" to Color(0xFFF08030),
    "Water" to Color(0xFF6890F0),
    "Electric" to Color(0xFFF8D030),
    "Ice" to Color(0xFF98D8D8),
    "Fighting" to Color(0xFFC03028),
    "Ground" to Color(0xFFE0C068),
    "Flying" to Color(0xFFA890F0),
    "Psychic" to Color(0xFFF85888),
    "Bug" to Color(0xFFA8B820),
    "Rock" to Color(0xFFB8A038),
    "Ghost" to Color(0xFF705898),
    "Dark" to Color(0xFF705848),
    "Dragon" to Color(0xFF7038F8),
    "Steel" to Color(0xFFB8B8D0),
    "Fairy" to Color(0xFFEE99AC),
    "Normal" to Color(0xFFA8A878)
)

@Composable
fun TypeBadge(type: String) {
    val capitalizedType = type.replaceFirstChar { it.titlecase() }
    val color = TypeColors[capitalizedType] ?: Color.Gray

    Text(
        text = capitalizedType,
        color = Color.White,
        modifier = Modifier
            .padding(5.dp)
            .background(color = color, shape = MaterialTheme.shapes.medium)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}