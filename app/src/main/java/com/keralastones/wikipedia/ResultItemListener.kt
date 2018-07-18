package com.keralastones.wikipedia

import android.content.Context

interface ResultItemListener {
    fun onItemClicked(result: SearchResult, context: Context)
}