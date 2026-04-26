package jimin.com.welog.core.video

import android.content.Context
import com.arthenica.ffmpegkit.FFmpegKit
import jimin.com.welog.data.HourSlot
import java.io.File

object VideoComposer {
    fun composeToday(
        context: Context,
        dateLabel: String,
        slots: List<HourSlot>,
        onComplete: (File?) -> Unit
    ) {
        val validSlots = slots.filter { it.ljm != null || it.jsh != null }
        if (validSlots.isEmpty()) {
            onComplete(null)
            return
        }
        val output = File(context.cacheDir, "welog_today_${System.currentTimeMillis()}.mp4")

        val inputArgs = mutableListOf<String>()
        val filterParts = mutableListOf<String>()
        val concatInputs = mutableListOf<String>()
        var inputIndex = 0
        var colorIndex = 0

        fun sourceLabel(path: String?, outLabel: String): String {
            if (path == null) {
                val label = "blk${colorIndex++}"
                filterParts += "color=c=black:s=1080x900:d=2[$label]"
                filterParts += "[$label]format=yuv420p[$outLabel]"
                return "[$outLabel]"
            }
            inputArgs += "-i '${path.replace("'", "\\'")}'"
            val inLabel = "${inputIndex++}:v"
            filterParts += "[$inLabel]scale=1080:900:force_original_aspect_ratio=decrease,pad=1080:900:(ow-iw)/2:(oh-ih)/2,trim=duration=2,setpts=PTS-STARTPTS,format=yuv420p[$outLabel]"
            return "[$outLabel]"
        }

        validSlots.forEachIndexed { idx, slot ->
            val hourText = "%02d:00".format(slot.hour)
            val topSource = sourceLabel(slot.ljm?.localPath, "top$idx")
            val bottomSource = sourceLabel(slot.jsh?.localPath, "bot$idx")
            val topCaption = escapeDrawText(slot.ljm?.caption.orEmpty().ifBlank { "💤" })
            val bottomCaption = escapeDrawText(slot.jsh?.caption.orEmpty().ifBlank { "💤" })
            filterParts += "$topSource" +
                "drawtext=text='${escapeDrawText(hourText)}':x=(w-text_w)/2:y=(h-text_h)/2-24:fontsize=54:fontcolor=white:box=0," +
                "drawtext=text='$topCaption':x=(w-text_w)/2:y=(h-text_h)/2+40:fontsize=44:fontcolor=white[toptxt$idx]"
            filterParts += "$bottomSource" +
                "drawtext=text='${escapeDrawText(hourText)}':x=(w-text_w)/2:y=(h-text_h)/2-24:fontsize=54:fontcolor=white:box=0," +
                "drawtext=text='$bottomCaption':x=(w-text_w)/2:y=(h-text_h)/2+40:fontsize=44:fontcolor=white[bottxt$idx]"
            filterParts += "[toptxt$idx][bottxt$idx]vstack=inputs=2[stack$idx]"
            concatInputs += "[stack$idx]"
        }

        val concatCount = validSlots.size
        filterParts += "${concatInputs.joinToString("")}concat=n=$concatCount:v=1:a=0[concated]"
        filterParts += "[concated]pad=1080:1920:0:120:black,drawtext=text='${escapeDrawText(dateLabel)}':x=(w-text_w)/2:y=45:fontsize=56:fontcolor=white[outv]"

        val command = buildString {
            append(inputArgs.joinToString(" "))
            append(" -filter_complex \"")
            append(filterParts.joinToString(";"))
            append("\" -map \"[outv]\" -an -r 30 -c:v libx264 -pix_fmt yuv420p ")
            append("'${output.absolutePath.replace("'", "\\'")}'")
        }

        FFmpegKit.executeAsync(command) { session ->
            if (session.returnCode.isValueSuccess) onComplete(output) else onComplete(null)
        }
    }

    private fun escapeDrawText(raw: String): String {
        return raw
            .replace("\\", "\\\\")
            .replace(":", "\\:")
            .replace("'", "\\'")
            .replace("%", "\\%")
    }
}
