package com.example.rustoreclone.ui.screenshots

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rustoreclone.data.repository.AppRepository

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenshotsScreen(
    appId: String,
    startIndex: Int,
    onBackClick: () -> Unit
) {
    val app = AppRepository().getAppById(appId)

    if (app == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Приложение не найдено")
        }
        return
    }

    val pagerState = rememberPagerState(
        initialPage = startIndex.coerceIn(0, app.screenshots.size - 1),
        pageCount = { app.screenshots.size }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "← Назад")
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card {
                    Text(
                        text = "Скриншот ${page + 1} из ${app.screenshots.size}",
                        modifier = Modifier.padding(64.dp)
                    )
                }
            }
        }
    }
}