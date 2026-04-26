package jimin.com.welog.feature.camera

import android.Manifest
import android.content.pm.PackageManager
import android.os.CountDownTimer
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.File

@Composable
fun CameraScreen(modifier: Modifier = Modifier) {
    val vm: CameraViewModel = viewModel()
    val state = vm.uiState
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    var previewViewRef by remember { mutableStateOf<PreviewView?>(null) }
    var videoCapture by remember { mutableStateOf<VideoCapture<Recorder>?>(null) }
    var currentRecording by remember { mutableStateOf<Recording?>(null) }

    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}
    val hasPermissions = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
    var requestedPermission by remember { mutableStateOf(false) }
    if (!hasPermissions && !requestedPermission) {
        SideEffect {
            requestedPermission = true
            launcher.launch(permissions)
        }
    }

    if (!hasPermissions) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("카메라/마이크 권한이 필요합니다.")
                Button(onClick = { launcher.launch(permissions) }) {
                    Text("권한 다시 요청")
                }
            }
        }
        return
    }

    LaunchedEffect(lensFacing, previewViewRef, hasPermissions) {
        if (!hasPermissions || previewViewRef == null) return@LaunchedEffect
        val providerFuture = ProcessCameraProvider.getInstance(context)
        providerFuture.addListener({
            val provider = providerFuture.get()
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewViewRef?.surfaceProvider
            }
            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.FHD))
                .build()
            val capture = VideoCapture.withOutput(recorder)
            provider.unbindAll()
            provider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.Builder().requireLensFacing(lensFacing).build(),
                preview,
                capture
            )
            videoCapture = capture
        }, ContextCompat.getMainExecutor(context))
    }

    if (state.capturedPath != null) {
        Column(modifier = modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AndroidView(
                factory = { ctx ->
                    VideoView(ctx).apply {
                        setVideoPath(state.capturedPath)
                        setOnPreparedListener { mp ->
                            mp.isLooping = true
                            start()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(240.dp)
            )
            OutlinedTextField(
                value = state.caption,
                onValueChange = vm::setCaption,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("문구 (최대 20자)") }
            )
            Button(onClick = { vm.saveClip {} }, modifier = Modifier.fillMaxWidth()) { Text("저장") }
            Button(onClick = vm::retake, modifier = Modifier.fillMaxWidth()) { Text("다시 찍기") }
        }
        return
    }

    if (state.isProcessing) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("영상 크롭 처리 중...")
        }
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).also { preview ->
                    previewViewRef = preview
                }
            },
            update = { previewViewRef = it },
            modifier = Modifier.fillMaxSize()
        )
        FrameOverlay(modifier = Modifier.fillMaxSize())
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isRecording) {
                LinearProgressIndicator(progress = { state.progress }, modifier = Modifier.fillMaxWidth())
            }
            Button(
                onClick = {
                    if (state.isRecording) return@Button
                    val output = File(context.cacheDir, "capture_${System.currentTimeMillis()}.mp4")
                    val capture = videoCapture ?: return@Button
                    runCatching {
                        val pending = capture.output
                            .prepareRecording(context, FileOutputOptions.Builder(output).build())
                        val canRecordAudio = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                        val recording = if (canRecordAudio) {
                            pending.withAudioEnabled()
                        } else {
                            pending
                        }.start(ContextCompat.getMainExecutor(context)) { event ->
                            if (event is VideoRecordEvent.Finalize && !event.hasError()) {
                                vm.onRecorded(output.absolutePath)
                            } else if (event is VideoRecordEvent.Finalize) {
                                vm.retake()
                            }
                        }
                        currentRecording = recording
                        vm.onRecordingStart()
                        object : CountDownTimer(2000, 100) {
                            override fun onTick(millisUntilFinished: Long) {
                                vm.onProgress((2000f - millisUntilFinished) / 2000f)
                            }
                            override fun onFinish() {
                                vm.onProgress(1f)
                                currentRecording?.stop()
                                currentRecording = null
                            }
                        }.start()
                    }.onFailure {
                        vm.retake()
                    }
                }
            ) { Text("2초 촬영") }
            Button(onClick = { lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK }) {
                Text("전/후면 전환")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { currentRecording?.stop() }
    }
}

@Composable
private fun FrameOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.background(Color.Transparent)) {
        val frameWidth = size.width * 0.92f
        val frameHeight = frameWidth * 9f / 16f
        val top = (size.height - frameHeight) / 2f
        val left = (size.width - frameWidth) / 2f
        drawRect(Color(0x88000000), topLeft = Offset(0f, 0f), size = androidx.compose.ui.geometry.Size(size.width, top))
        drawRect(
            Color(0x88000000),
            topLeft = Offset(0f, top + frameHeight),
            size = androidx.compose.ui.geometry.Size(size.width, size.height - (top + frameHeight))
        )
        drawRect(Color(0x88000000), topLeft = Offset(0f, top), size = androidx.compose.ui.geometry.Size(left, frameHeight))
        drawRect(
            Color(0x88000000),
            topLeft = Offset(left + frameWidth, top),
            size = androidx.compose.ui.geometry.Size(size.width - (left + frameWidth), frameHeight)
        )
        drawLine(Color.White, Offset(left, top), Offset(left + frameWidth, top), strokeWidth = 3f)
        drawLine(Color.White, Offset(left, top + frameHeight), Offset(left + frameWidth, top + frameHeight), strokeWidth = 3f)
    }
}
