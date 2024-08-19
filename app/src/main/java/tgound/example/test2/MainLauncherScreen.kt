package tgound.example.test2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainLauncherScreen(viewModel: LauncherViewModel) {
    val apps by viewModel.apps.collectAsState()
    val groupedApps = remember(apps) { groupAppsIntoFolders(apps) }
    val onLaunchApp = remember { viewModel::launchApp }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Recently Used Apps", style = MaterialTheme.typography.titleMedium)
        RecentlyUsedApps(viewModel)

        Text("Folder", style = MaterialTheme.typography.titleMedium)
        LazyRow(contentPadding = PaddingValues(8.dp)) {
            items(
                items = groupedApps.toList(),
                key = { (folderName, _) -> folderName }
            ) { (folderName, appsInFolder) ->
                AppFolder(folderName, appsInFolder, onLaunchApp)
            }
        }


        Text("All apps", style = MaterialTheme.typography.titleMedium)
        LauncherScreen(viewModel)
    }
}
