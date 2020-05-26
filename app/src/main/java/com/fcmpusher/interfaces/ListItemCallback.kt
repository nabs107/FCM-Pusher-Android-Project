package com.fcmpusher.interfaces

import com.fcmpusher.model.App

interface ListItemCallback {
    fun itemClicked(item: App)
}