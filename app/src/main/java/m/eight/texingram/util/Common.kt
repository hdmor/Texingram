package m.eight.texingram.util

import android.text.format.DateUtils.DAY_IN_MILLIS
import android.text.format.DateUtils.HOUR_IN_MILLIS
import android.text.format.DateUtils.MINUTE_IN_MILLIS

fun convertToTimeAgo(time: Long): String {

    var calcTime = time

    if (time < 1_000_000_000_000L) calcTime *= 1000L

    val now = System.currentTimeMillis()
    if (calcTime > now || calcTime <= 0) return "unknown"

    val diff = now - calcTime
    return if (diff < MINUTE_IN_MILLIS) "just now"
    else if (diff < 2 * MINUTE_IN_MILLIS) "a minute ago"
    else if (diff < 50 * MINUTE_IN_MILLIS) "${diff / MINUTE_IN_MILLIS} minutes ago"
    else if (diff < 90 * MINUTE_IN_MILLIS) "an hour ago"
    else if (diff < 24 * HOUR_IN_MILLIS) "${diff / HOUR_IN_MILLIS} hours ago"
    else if (diff < 48 * HOUR_IN_MILLIS) "yesterday"
    else "${diff / DAY_IN_MILLIS} days ago"
}