package com.example.rustoreclone.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rustoreclone.data.model.App
import com.example.rustoreclone.data.repository.AppRepository
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onAppClick: (String) -> Unit,
    onCategoriesClick: () -> Unit,
    onSearchClick: () -> Unit,
    categoryFilter: String = "",
    repository: AppRepository = AppRepository()
) {
    var isRefreshing by remember { mutableStateOf(false) }
    var apps by remember {
        mutableStateOf(
            if (categoryFilter.isBlank()) repository.getAllApps()
            else repository.getAppsByCategory(
                com.example.rustoreclone.data.model.Category.valueOf(categoryFilter)
            )
        )
    }

    val title = if (categoryFilter.isBlank()) "RuStore"
    else com.example.rustoreclone.data.model.Category.valueOf(categoryFilter).displayName

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                },
                navigationIcon = {
                    if (categoryFilter.isNotBlank()) {
                        IconButton(onClick = onCategoriesClick) {
                            Text(text = "←", fontSize = 24.sp)
                        }
                    }
                },
                actions = {
                    if (categoryFilter.isBlank()) {
                        TextButton(onClick = onSearchClick) {
                            Text(text = "🔍", fontSize = 20.sp)
                        }
                        TextButton(onClick = onCategoriesClick) {
                            Text(
                                text = "Категории",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                apps = if (categoryFilter.isBlank()) repository.getAllApps()
                else repository.getAppsByCategory(
                    com.example.rustoreclone.data.model.Category.valueOf(categoryFilter)
                )
                isRefreshing = false
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (apps.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "😕", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Приложений не найдено",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(apps) { app ->
                        AppCard(app = app, onClick = { onAppClick(app.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun AppCard(app: App, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = iconColor(app.name),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = app.name.take(1),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = app.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    CategoryBadge(text = app.category.displayName)
                    CategoryBadge(text = app.ageRating.label)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(text = "Открыть", fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun CategoryBadge(text: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

fun iconColor(name: String): Color {
    val colors = listOf(
        Color(0xFF6200EE), Color(0xFF03DAC5), Color(0xFFE91E63),
        Color(0xFF3F51B5), Color(0xFF4CAF50), Color(0xFFFF5722), Color(0xFF009688)
    )
    return colors[name.length % colors.size]
}