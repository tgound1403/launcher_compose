package tgound.example.test2

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AppItem(app: AppInfo, onAppClick: ((Context, String) -> Unit)?, isShowName: Boolean = true) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(if(isShowName)8.dp else 0.dp)
            .clickable {
                if (onAppClick != null) {
                    onAppClick(context, app.packageName)
                }
            }
    ) {
        AsyncImage(
            model = app.icon,
            contentDescription = app.name,
            modifier = Modifier.size(48.dp)
        )
        if(isShowName) Text(
            text = app.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
