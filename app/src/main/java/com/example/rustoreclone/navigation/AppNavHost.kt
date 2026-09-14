package com.example.rustoreclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rustoreclone.ui.appdetail.AppDetailScreen
import com.example.rustoreclone.ui.catalog.CatalogScreen
import com.example.rustoreclone.ui.categories.CategoriesScreen
import com.example.rustoreclone.ui.onboarding.OnboardingScreen
import com.example.rustoreclone.ui.screenshots.ScreenshotsScreen
import com.example.rustoreclone.ui.search.SearchScreen

@Composable
fun AppNavHost(showOnboarding: Boolean = true) {
    val navController: NavHostController = rememberNavController()
    val startDestination = if (showOnboarding) Screen.Onboarding.route else Screen.Catalog.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Catalog.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Catalog.route) {
            CatalogScreen(
                onAppClick = { appId ->
                    navController.navigate(Screen.AppDetail.createRoute(appId))
                },
                onCategoriesClick = {
                    navController.navigate(Screen.Categories.route)
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                onBackClick = { navController.popBackStack() },
                onCategoryClick = { category ->
                    navController.navigate(Screen.CatalogFiltered.createRoute(category.name))
                }
            )
        }

        composable(Screen.CatalogFiltered.route) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CatalogScreen(
                categoryFilter = categoryName,
                onAppClick = { appId ->
                    navController.navigate(Screen.AppDetail.createRoute(appId))
                },
                onCategoriesClick = { navController.popBackStack() },
                onSearchClick = { navController.navigate(Screen.Search.route) }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onBackClick = { navController.popBackStack() },
                onAppClick = { appId ->
                    navController.navigate(Screen.AppDetail.createRoute(appId))
                }
            )
        }

        composable(Screen.AppDetail.route) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: ""
            AppDetailScreen(
                appId = appId,
                onBackClick = { navController.popBackStack() },
                onScreenshotClick = { index ->
                    navController.navigate(Screen.Screenshots.createRoute(appId, index))
                }
            )
        }

        composable(Screen.Screenshots.route) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId") ?: ""
            val startIndex = backStackEntry.arguments?.getString("startIndex")?.toIntOrNull() ?: 0
            ScreenshotsScreen(
                appId = appId,
                startIndex = startIndex,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}