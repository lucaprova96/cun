package com.luce.models

import com.luce.adapters.AppAdapter

sealed interface Show : AppAdapter.Item {
    var isFavorite: Boolean
}
