package com.example.rustoreclone.data.model

enum class Category(val displayName: String) {
    FINANCE("Финансы"),
    TOOLS("Инструменты"),
    GAMES("Игры"),
    GOVERNMENT("Государственные"),
    TRANSPORT("Транспорт")
}
enum class AgeRating(val label: String){
    ZERO_PLUS("0+"),
    SIX_PLUS("6+"),
    EIGHT_PLUS("8+"),
    TWELVE_PLUS("12+"),
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+")
}

data class App(
    val id: String,
    val name: String,
    val developer: String,
    val category: Category,
    val ageRating: AgeRating,
    val iconUrl: String,
    val shortDescription: String,
    val fullDescription: String,
    val screenshots: List<String>
)