package com.example.agenteqr2.core

import android.view.View

fun View.showIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}