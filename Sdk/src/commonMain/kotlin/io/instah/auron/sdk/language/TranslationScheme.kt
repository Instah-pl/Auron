package io.instah.auron.sdk.language

import kotlin.reflect.KProperty

abstract class TranslationScheme(
    val defaultLanguage: Language
) {
    val translations = mutableMapOf<String, String>()
    internal val translationClasses = mutableMapOf<Language, Translation<*>>()
    abstract fun setTranslationClasses()

    @Suppress("ClassName")
    inner class field(
        val defaultTranslation: String
    ) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = translations[property.name] ?: defaultTranslation
    }

    init { setTranslationClasses() }
}