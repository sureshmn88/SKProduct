package com.sk.myproduct.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(msg: String, type: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(msg: String, type: Int, isShow: Boolean) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}


fun String.checkTrim(): String {
    return this.trim()
}