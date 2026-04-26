package jimin.com.welog.feature.camera

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import jimin.com.welog.core.video.CameraCropper
import jimin.com.welog.core.prefs.UserPreferences
import jimin.com.welog.data.ClipRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class CameraUiState(
    val isRecording: Boolean = false,
    val progress: Float = 0f,
    val capturedPath: String? = null,
    val caption: String = "",
    val isProcessing: Boolean = false
)

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ClipRepository.get(application)
    private val userPrefs = UserPreferences(application)

    var uiState by mutableStateOf(CameraUiState())
        private set

    fun onRecordingStart() {
        uiState = uiState.copy(isRecording = true, progress = 0f, capturedPath = null)
    }

    fun onProgress(progress: Float) {
        uiState = uiState.copy(progress = progress)
    }

    fun onRecorded(rawPath: String) {
        uiState = uiState.copy(isRecording = false, progress = 1f, isProcessing = true)
        viewModelScope.launch(Dispatchers.IO) {
            val output = "${rawPath.substringBeforeLast(".mp4")}_crop.mp4"
            val success = CameraCropper.cropToLandscape16by9(rawPath, output)
            uiState = uiState.copy(
                capturedPath = if (success) output else rawPath,
                isProcessing = false
            )
        }
    }

    fun setCaption(caption: String) {
        uiState = uiState.copy(caption = caption.take(20))
    }

    fun retake() {
        uiState = uiState.copy(
            capturedPath = null,
            caption = "",
            progress = 0f,
            isRecording = false,
            isProcessing = false
        )
    }

    fun saveClip(onDone: () -> Unit) {
        val path = uiState.capturedPath ?: return
        val user = userPrefs.getUser() ?: return
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveClip(user.userId, uiState.caption, path)
            uiState = uiState.copy(capturedPath = null, caption = "", progress = 0f, isProcessing = false)
            onDone()
        }
    }
}
