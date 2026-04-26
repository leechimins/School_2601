package jimin.com.welog.feature.onboarding

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import jimin.com.welog.core.prefs.UserPreferences

class OnboardingViewModel(application: Application) : AndroidViewModel(application) {
    private val userPrefs = UserPreferences(application)
    private val _isOnboardingDone = mutableStateOf(userPrefs.getUser() != null)
    val isOnboardingDone: State<Boolean> = _isOnboardingDone

    fun complete(selectedName: String, emoji: String) {
        val (id, name) = if (selectedName == "정수한") "JSH" to "정수한" else "LJM" to "이지민"
        userPrefs.saveUser(id = id, name = name, emoji = emoji)
        _isOnboardingDone.value = true
    }
}
