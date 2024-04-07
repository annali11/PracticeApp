package com.example.practiceapp

import java.util.Locale
import android.content.Context
import android.content.ContextWrapper

class LanguageHelper {

    companion object {
        fun changeLanguage(context: Context, languageCode: String): ContextWrapper {

            var context1 = context
            val resources = context.resources
            val configuration = resources.configuration
            val systemLocale = configuration.locales[0]

            if (languageCode != "" && languageCode != systemLocale.language){
                val locale = Locale(languageCode)
                Locale.setDefault(locale)
                configuration.setLocale(locale)
                context1 = context1.createConfigurationContext(configuration)
            }

            return ContextWrapper(context1)
        }
    }
}