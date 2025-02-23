package io.instah.auron.sdk.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.reflect.KProperty

object TranslationManager {
    //Taken over means that the automatic language switching would be disabled
    var takenOver = false
    var currentLanguage: Language? by mutableStateOf(null)
    private var schemeHolders = mutableListOf<schemeHolder<*>>()

    fun setLanguage(language: Language) {
        schemeHolders.forEach {
            configureTranslationSchemeHolder(
                it, Language.pl_PL
            )
        }
        currentLanguage = language
    }

    fun <S : TranslationScheme, H : schemeHolder<S>> configureTranslationSchemeHolder(
        holder: H,
        language: Language
    ) {
        val newScheme = holder.createScheme()
        newScheme.applyLanguage(language)
        holder.scheme = newScheme
    }

    @Suppress("ClassName")
    class schemeHolder<S : TranslationScheme>(
        val createScheme: () -> S
    ) {
        var scheme: S? = null

        operator fun getValue(thisRef: Any?, property: KProperty<*>): S {
            if (scheme == null) {
                scheme = createScheme()
                schemeHolders.add(this)
            }

            return scheme!!
        }
    }
}