package com.example.newsapp.fragments.setting

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel

class SettingViewModel() : ViewModel() {

    var isExpanded by mutableStateOf(false)

    val systemLanguage = when (Locale.current.language) {
        "en" -> "English"
        "ar" -> "العربية"
        else -> {
            "English"
        }
    }
    val currentLanguage = AppCompatDelegate.getApplicationLocales()[0]?.displayLanguage ?: systemLanguage

    var selectedLanguage by mutableStateOf(currentLanguage)


}