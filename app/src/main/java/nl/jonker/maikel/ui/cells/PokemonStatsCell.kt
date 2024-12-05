package nl.jonker.maikel.ui.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.jonker.maikel.models.DetailedPokemon
import nl.jonker.maikel.models.Stats

@Composable
fun PokemonStatsCell(detail: DetailedPokemon) {

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp)) {

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.background(color = Color.White).padding(40.dp)
        ) {
            val tabs = listOf("About", "Stats")
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedTabIndex == index) Color(0xFF6200EE) else Color.Gray
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            when (selectedTabIndex) {
                0 -> AboutSection(detail)
                1 -> StatsSection(detail.stats)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AboutSection(detail: DetailedPokemon) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        AboutRow(label = "Name", value = detail.name.replaceFirstChar { it.titlecase() })
        AboutRow(label = "ID", value = String.format("%03d", detail.id))
        AboutRow(label = "Base Experience", value = "${detail.baseExperience} XP")
        AboutRow(label = "Weight", value = "${detail.weight / 10.0} kg") // Convert to kg
        AboutRow(label = "Height", value = "${detail.height / 10.0} m") // Convert to meters
        AboutRow(label = "Types", value = detail.types.joinToString(", "))
        AboutRow(label = "Abilities", value = detail.abilities.joinToString(", "))
    }
}

@Composable
fun AboutRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(150.dp)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun StatsSection(stats: Stats) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        StatRow(label = "HP", value = stats.hp)
        StatProgressBar(stats.hp)
        StatRow(label = "Attack", value = stats.attack)
        StatProgressBar(stats.attack)
        StatRow(label = "Defense", value = stats.defense)
        StatProgressBar(stats.defense)
        StatRow(label = "Special Attack", value = stats.specialAttack)
        StatProgressBar(stats.specialAttack)
        StatRow(label = "Special Defense", value = stats.specialDefense)
        StatProgressBar(stats.specialDefense)
        StatRow(label = "Speed", value = stats.speed)
        StatProgressBar(stats.speed)
    }
}

@Composable
fun StatProgressBar(stat: Int){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 5.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(stat.toFloat()/200)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(size = 5.dp)
                )
        )
    }
}

@Composable
fun StatRow(label: String, value: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        Text(value.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}