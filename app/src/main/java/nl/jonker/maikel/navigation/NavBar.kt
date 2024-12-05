package nl.jonker.maikel.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

private data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val navigationLink: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(navController: NavController){
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            navigationLink = { navController.navigate("Pokedex") }
        ),
        BottomNavigationItem(
            title = "Favourites",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            navigationLink = { navController.navigate("favourites") }
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        NavigationBar() {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        item.navigationLink()
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) }
                )
            }
        }
    }
}