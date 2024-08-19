package tgound.example.test2
import android.content.Context
import android.graphics.drawable.Drawable

data class AppInfo(val name: String, val packageName: String, val icon: Drawable)

fun AppInfo.launch(context: Context) {
    val intent = context.packageManager.getLaunchIntentForPackage(packageName) ?: return
    context.startActivity(intent)
}
