package com.example.rustoreclone.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Catalog : Screen("catalog")
    object Categories : Screen("categories")
    object Search : Screen("search")

    object CatalogFiltered : Screen("catalog_filtered/{categoryName}") {
        fun createRoute(categoryName: String) = "catalog_filtered/$categoryName"
    }

    object AppDetail : Screen("app_detail/{appId}") {
        fun createRoute(appId: String) = "app_detail/$appId"
    }

    object Screenshots : Screen("screenshots/{appId}/{startIndex}") {
        fun createRoute(appId: String, startIndex: Int) = "screenshots/$appId/$startIndex"
    }
}