package tgound.example.test2

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    private val hasPermission = mutableStateOf(false)
    private val viewModel: LauncherViewModel by viewModels()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (checkUsageStatsPermission()) {
            hasPermission.value = true
        } else {
            Toast.makeText(this, "Quyền bị từ chối. Ứng dụng sẽ đóng.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }

        checkAndRequestPermission()
    }

    @Composable
    private fun MainContent() {
        if (hasPermission.value) {
            MainLauncherScreen(viewModel)
        } else {
            // Hiển thị một màn hình loading hoặc thông báo đang yêu cầu quyền
            LoadingScreen()
        }
    }

    private fun checkAndRequestPermission() {
        if (checkUsageStatsPermission()) {
            hasPermission.value = true
        } else {
            requestUsageStatsPermission()
        }
    }

    private fun checkUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestUsageStatsPermission() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        permissionLauncher.launch(intent)
    }
}

@Composable
fun LoadingScreen() {
    // Implement a loading screen or permission request screen
}
