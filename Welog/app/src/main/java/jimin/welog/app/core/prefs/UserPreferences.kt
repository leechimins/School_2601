package jimin.com.welog.core.prefs

import android.content.Context

data class WelogUser(
    val userId: String,
    val userName: String,
    val userEmoji: String
)

class UserPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("welog_prefs", Context.MODE_PRIVATE)

    fun getUser(): WelogUser? {
        val id = prefs.getString("userID", null) ?: return null
        val name = prefs.getString("userName", null) ?: return null
        val emoji = prefs.getString("userEmoji", defaultEmojiFor(id)) ?: defaultEmojiFor(id)
        return WelogUser(id, name, emoji)
    }

    fun saveUser(id: String, name: String, emoji: String?) {
        prefs.edit()
            .putString("userID", id)
            .putString("userName", name)
            .putString("userEmoji", emoji?.take(1)?.ifBlank { defaultEmojiFor(id) } ?: defaultEmojiFor(id))
            .apply()
    }

    private fun defaultEmojiFor(id: String): String = if (id == "JSH") "🐻" else "🐰"
}
