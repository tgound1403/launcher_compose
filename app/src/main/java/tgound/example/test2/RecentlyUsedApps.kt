package tgound.example.test2

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp

@Composable
fun RecentlyUsedApps(viewModel: LauncherViewModel) {
    val recentApps by viewModel.recentApps.collectAsState()

    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(recentApps) { app ->
            AppItem(app, viewModel::launchApp)
        }
    }
}
