package io.instah.auron.sdk.language

import kotlin.reflect.KProperty

abstract class Translation<S : TranslationScheme>(
    val language: Language
) {
    abstract fun SetTranslationsScope.setTranslations()

    inner class SetTranslationsScope(
        val scheme: S
    ) {
        operator fun invoke(
            body: (SetTranslationsScope) -> Unit
        ) = body(this)

        fun KProperty<*>.setTranslation(
            translation: String
        ) {
            scheme.translations[this@setTranslation.name] = translation
        }
    }
}