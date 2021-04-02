package skywolf46.itemexpire.util

import java.util.concurrent.TimeUnit

object TimeString {
    fun toDHMS(ms: Long): String {
        var day = TimeUnit.MILLISECONDS.toDays(ms)
        var hour = TimeUnit.MILLISECONDS.toHours(ms) - (day * 24)
        var min = TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.MILLISECONDS.toHours(ms)
        var sec = TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MILLISECONDS.toMinutes(ms)
        var builder = StringBuilder()
        if (day > 0)
            builder.append("$day ${if (day > 0) "Day" else "Days"} ")

        if (hour > 0)
            builder.append("$hour ${if (hour > 0) "Hour" else "Hours"} ")

        if (min > 0)
            builder.append("$min ${if (min > 0) "Minute" else "Minutes"} ")


        if (sec > 0)
            builder.append("$sec ${if (sec > 0) "Second" else "Seconds"} ")
        if (builder.isEmpty())
            return "0 Seconds"
        return builder.toString().trim()
    }
}