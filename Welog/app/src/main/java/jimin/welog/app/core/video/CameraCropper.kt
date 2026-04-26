package jimin.com.welog.core.video

import com.arthenica.ffmpegkit.FFmpegKit
import java.io.File

object CameraCropper {
    fun cropToLandscape16by9(inputPath: String, outputPath: String): Boolean {
        val command = buildString {
            append("-y -i ")
            append("'${inputPath.replace("'", "\\'")}' ")
            append("-vf ")
            append("\"crop='if(gte(iw/ih,16/9),ih*16/9,iw)':'if(gte(iw/ih,16/9),ih,iw*9/16)':(iw-ow)/2:(ih-oh)/2,scale=1280:720,setsar=1\" ")
            append("-an -c:v libx264 -preset veryfast -crf 22 ")
            append("'${outputPath.replace("'", "\\'")}'")
        }
        val session = FFmpegKit.execute(command)
        return session.returnCode.isValueSuccess && File(outputPath).exists()
    }
}
