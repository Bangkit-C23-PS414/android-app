package com.bangkit.coffee.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date

fun Date.toTimeString(): String {
    return SimpleDateFormat.getTimeInstance(DateFormat.SHORT).format(this)
}

fun Date.toDateString(): String {
    return SimpleDateFormat.getDateInstance(DateFormat.DEFAULT).format(this)
}

fun Date.toDateTimeString(): String {
    return SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(this)
}

fun LocalDateTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
}

fun LocalDateTime.toDateString(): String {
    return this.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
}

fun LocalDateTime.toDateTimeString(): String {
    return this.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT))
}

fun Long.toAdjustedLocalDate(): LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        Clock.systemUTC().zone
    )
}