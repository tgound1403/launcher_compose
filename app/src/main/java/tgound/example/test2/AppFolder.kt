package tgound.example.test2

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AppFolder(folderName: String, apps: List<AppInfo>, onAppClick: (Context, String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { isExpanded = true }
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(15.dp),
                    color = Color.Gray
                )
                .width(100.dp)
                .height(100.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
            ) {
                items(apps) { app ->
                    AppItem(app, null, isShowName = false)
                }
            }
        }
        Text(
            text = "Apps $folderName",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

    if (isExpanded) {
        Dialog(onDismissRequest = { isExpanded = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column {
                    Text(
                        text = "Apps $folderName",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(count = 3),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(apps) { app ->
                            AppItem(app, onAppClick)
                        }
                    }
                }
            }
        }
    }
}
