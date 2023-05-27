package com.bangkit.coffee.util

import java.text.DateFormat
import java.text.SimpleDateFormat
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