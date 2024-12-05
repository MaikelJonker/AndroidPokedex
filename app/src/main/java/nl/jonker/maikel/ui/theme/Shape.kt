package nl.jonker.maikel.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),      // Small components like chips or labels
    medium = RoundedCornerShape(12.dp),    // Medium-sized components like cards
    large = RoundedCornerShape(16.dp)      // Larger elements if needed
)