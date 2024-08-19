package tgound.example.test2

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp

@Composable
fun LauncherScreen(viewModel: LauncherViewModel) {
    val apps by viewModel.apps.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 80.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(apps) { app ->
            AppItem(app, viewModel::launchApp)
        }
    }
}
