package com.luce.models

import com.luce.adapters.AppAdapter

open class Provider(
    val name: String,
    val logo: String,
    val language: String,

    val provider: com.luce.providers.Provider,
    var isFavorite: Boolean = false,
) : AppAdapter.Item {


    override lateinit var itemType: AppAdapter.Type
}