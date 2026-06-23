package jimin.com.welog.ui

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import jimin.com.welog.feature.camera.CameraScreen
import jimin.com.welog.feature.home.HomeScreen
import jimin.com.welog.feature.onboarding.OnboardingScreen
import jimin.com.welog.feature.onboarding.OnboardingViewModel

private enum class Tab(val label: String) {
    Home("홈"),
    Camera("촬영")
}

@Composable
fun WelogApp(launchIntent: Intent?) {
    val onboardingViewModel: OnboardingViewModel = viewModel()
    val isOnboardingDone by onboardingViewModel.isOnboardingDone
    if (!isOnboardingDone) {
        OnboardingScreen(onboardingViewModel)
        return
    }

    var selectedTab by rememberSaveable {
        mutableStateOf(
            if (launchIntent?.getBooleanExtra("open_camera_tab", false) == true) Tab.Camera else Tab.Home
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Text(if (tab == Tab.Home) "🏠" else "📷") },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            Tab.Home -> HomeScreen(modifier = Modifier.padding(innerPadding))
            Tab.Camera -> CameraScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
