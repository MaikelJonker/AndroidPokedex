package nl.jonker.maikel.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.jonker.maikel.ui.detail.DetailPage
import nl.jonker.maikel.ui.detail.PokemonDetailViewModel
import nl.jonker.maikel.ui.error.ErrorPage
import nl.jonker.maikel.ui.favourites.FavouritesPage
import nl.jonker.maikel.ui.favourites.FavouritesViewModel
import nl.jonker.maikel.ui.pokedex.Pokedex

@Composable
fun App() {
    val navController = rememberNavController()
    val detailViewModel: PokemonDetailViewModel = hiltViewModel()
    val favouritesViewModel: FavouritesViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background ),
        bottomBar = {
            when (currentRoute) {
                "pokedex", "favourites" -> NavBar(navController = navController)
                else -> {}
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "pokedex",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("pokedex") {
                Pokedex(
                    onNavigateToDetail = { id ->
                            navController.navigate("detail/$id")
                    },
                    toErrorPage = { navController.navigate("error/pokedex") }
                )
            }
            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                DetailPage(
                    pokemonId = id,
                    viewModel = detailViewModel,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("favourites"){
                FavouritesPage(
                    viewModel = favouritesViewModel,
                    onNavigateToDetail = { id -> navController.navigate("detail/$id")
                })
            }
            composable("error/{page}"){ backStackEntry ->
                val page = backStackEntry.arguments?.getString("page") ?: "pokedex"
                ErrorPage(
                    onRefresh = { navController.navigate(page) }
                )
            }
        }
    }
}
