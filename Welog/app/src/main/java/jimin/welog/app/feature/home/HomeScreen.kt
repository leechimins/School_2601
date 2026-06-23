package jimin.com.welog.feature.home

import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import jimin.com.welog.core.prefs.UserPreferences
import jimin.com.welog.core.video.MediaStoreSaver
import jimin.com.welog.core.video.VideoComposer
import jimin.com.welog.data.ClipRepository
import jimin.com.welog.data.HourSlot
import jimin.com.welog.data.local.ClipEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val user = remember { UserPreferences(context).getUser() }
    val repository = remember { ClipRepository.get(context) }
    val today = remember { SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Date()) }
    val scope = rememberCoroutineScope()
    var slots by remember { mutableStateOf(listOf<HourSlot>()) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var isComposing by remember { mutableStateOf(false) }
    var composeResultText by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        repository.observeTodaySlots().collectLatest { newSlots ->
            slots = newSlots
            currentIndex = slots.lastIndex.coerceAtLeast(0)
        }
    }

    val shownSlot = slots.getOrNull(currentIndex)
    val shownHour = shownSlot?.hour ?: 0
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("${user?.userEmoji ?: "🙂"} $today ${"%02d".format(shownHour)}:00")
        Button(
            onClick = {
                if (isComposing) return@Button
                isComposing = true
                scope.launch {
                    val todaySlots = withContext(Dispatchers.IO) { repository.getTodaySlots() }
                    VideoComposer.composeToday(context, repository.todayDateLabel(), todaySlots) { output ->
                        isComposing = false
                        if (output == null) {
                            composeResultText = "합성 실패 또는 대상 슬롯이 없습니다."
                            return@composeToday
                        }
                        val saved = MediaStoreSaver.saveMp4(context, output)
                        composeResultText = if (saved) "갤러리에 저장되었습니다." else "갤러리 저장에 실패했습니다."
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("오늘 영상 완성하기")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .pointerInput(slots, currentIndex) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 40 && currentIndex > 0) currentIndex--
                        if (dragAmount < -40 && currentIndex < slots.lastIndex) currentIndex++
                    }
                },
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            ClipPanel(clip = shownSlot?.ljm, hour = shownHour, fallbackLabel = "LJM", modifier = Modifier.weight(1f))
            ClipPanel(clip = shownSlot?.jsh, hour = shownHour, fallbackLabel = "JSH", modifier = Modifier.weight(1f))
        }
    }

    if (isComposing) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("합성 중") },
            text = { CircularProgressIndicator() },
            confirmButton = {}
        )
    }
    if (composeResultText != null) {
        AlertDialog(
            onDismissRequest = { composeResultText = null },
            title = { Text("오늘 영상 완성하기") },
            text = { Text(composeResultText.orEmpty()) },
            confirmButton = { Button(onClick = { composeResultText = null }) { Text("확인") } }
        )
    }
}

@Composable
private fun ClipPanel(
    clip: ClipEntity?,
    hour: Int,
    fallbackLabel: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (clip != null) {
            AndroidView(
                factory = { ctx ->
                    VideoView(ctx).apply {
                        tag = clip.localPath
                        setVideoPath(clip.localPath)
                        setOnPreparedListener { mp ->
                            mp.isLooping = true
                            start()
                        }
                    }
                },
                update = { videoView ->
                    if (videoView.tag != clip.localPath) {
                        videoView.tag = clip.localPath
                        videoView.setVideoPath(clip.localPath)
                    }
                    videoView.setOnPreparedListener { mp ->
                        mp.isLooping = true
                        videoView.start()
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${"%02d".format(hour)}:00", color = Color.White)
                if (clip.caption.isNotBlank()) {
                    Text(text = clip.caption, color = Color.White, modifier = Modifier.padding(top = 4.dp))
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = fallbackLabel, color = Color.White)
                Text(text = "${"%02d".format(hour)}:00", color = Color.White)
                Text(text = "💤", color = Color.White, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}
