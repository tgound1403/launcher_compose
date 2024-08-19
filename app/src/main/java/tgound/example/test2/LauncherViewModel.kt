package tgound.example.test2

import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LauncherViewModel(application: Application) : AndroidViewModel(application) {
    private val _apps = MutableStateFlow<List<AppInfo>>(emptyList())
    private val _recentApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val apps: StateFlow<List<AppInfo>> = _apps.asStateFlow()
    val recentApps: StateFlow<List<AppInfo>> = _recentApps.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _apps.value = getInstalledApps()
            _recentApps.value = getRecentlyUsedApps()
        }
    }

    fun launchApp(context: Context, packageName: String) {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, "Error on open $packageName", Toast.LENGTH_LONG).show()
        }
    }


    private suspend fun getInstalledApps(): List<AppInfo> = withContext(Dispatchers.Default) {
        val pm = getApplication<Application>().packageManager

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val activities: List<ResolveInfo> = pm.queryIntentActivities(
            intent,
            0
        )

        activities.map { resolveInfo ->
            AppInfo(
                name = resolveInfo.loadLabel(pm).toString(),
                packageName = resolveInfo.activityInfo.packageName,
                icon = resolveInfo.loadIcon(pm)
            )
        }
    }

    private suspend fun getRecentlyUsedApps(limit: Int = 5): List<AppInfo> =
        withContext(Dispatchers.Default) {
            val context = getApplication<Application>()
            val pm = context.packageManager
            val usageStatsManager =
                context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

            val currentTime = System.currentTimeMillis()
            val startTime = currentTime - 7 * 24 * 60 * 60 * 1000 // 7 days ago

            val usageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST,
                startTime,
                currentTime
            )

            usageStats
                .sortedByDescending { it.lastTimeUsed }
                .distinctBy { it.packageName }
                .take(limit)
                .mapNotNull { stats ->
                    try {
                        val appInfo = pm.getApplicationInfo(stats.packageName, 0)
                        AppInfo(
                            name = pm.getApplicationLabel(appInfo).toString(),
                            packageName = stats.packageName,
                            icon = pm.getApplicationIcon(appInfo)
                        )
                    } catch (e: PackageManager.NameNotFoundException) {
                        null
                    }
                }
        }
}
