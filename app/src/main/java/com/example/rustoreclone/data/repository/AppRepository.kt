package com.example.rustoreclone.data.repository

import com.example.rustoreclone.data.model.AgeRating
import com.example.rustoreclone.data.model.App
import com.example.rustoreclone.data.model.Category

class AppRepository {

    private val fakeApps = listOf(
        App(
            id = "1",
            name = "СуперБанк",
            developer = "SuperBank LLC",
            category = Category.FINANCE,
            ageRating = AgeRating.ZERO_PLUS,
            iconUrl = "",
            shortDescription = "Удобный мобильный банк",
            fullDescription = "СуперБанк — полнофункциональное банковское приложение с переводами, вкладами и картами.",
            screenshots = listOf("", "", "")
        ),
        App(
            id = "2",
            name = "ТаксиГо",
            developer = "GoTech",
            category = Category.TRANSPORT,
            ageRating = AgeRating.ZERO_PLUS,
            iconUrl = "",
            shortDescription = "Заказ такси за секунды",
            fullDescription = "ТаксиГо помогает быстро найти водителя рядом и оплатить поездку прямо в приложении.",
            screenshots = listOf("", "", "")
        ),
        App(
            id = "3",
            name = "МояИгра",
            developer = "Indie Studio",
            category = Category.GAMES,
            ageRating = AgeRating.TWELVE_PLUS,
            iconUrl = "",
            shortDescription = "Аркадная игра для всей семьи",
            fullDescription = "МояИгра — увлекательная аркада с простым управлением и яркой графикой.",
            screenshots = listOf("", "", "")
        ),
        App(
            id = "4",
            name = "Госуслуги Lite",
            developer = "GovDev",
            category = Category.GOVERNMENT,
            ageRating = AgeRating.ZERO_PLUS,
            iconUrl = "",
            shortDescription = "Доступ к госуслугам онлайн",
            fullDescription = "Упрощённый доступ к государственным услугам: справки, записи, документы.",
            screenshots = listOf("", "", "")
        ),
        App(
            id = "5",
            name = "Заметки+",
            developer = "ToolsCorp",
            category = Category.TOOLS,
            ageRating = AgeRating.ZERO_PLUS,
            iconUrl = "",
            shortDescription = "Простые и быстрые заметки",
            fullDescription = "Заметки+ — минималистичное приложение для быстрых заметок и списков дел.",
            screenshots = listOf("", "", "")
        )
    )

    fun getAllApps(): List<App> = fakeApps

    fun getAppById(id: String): App? = fakeApps.find { it.id == id }

    fun getAppsByCategory(category: Category): List<App> =
        fakeApps.filter { it.category == category }

    fun searchApps(query: String): List<App> =
        fakeApps.filter { it.name.contains(query, ignoreCase = true) }
}