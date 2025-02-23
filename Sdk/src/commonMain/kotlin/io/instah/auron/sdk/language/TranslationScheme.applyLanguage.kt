package io.instah.auron.sdk.language

fun <S : TranslationScheme> S.applyLanguage(language: Language) {
    @Suppress("UNCHECKED_CAST")
    val translations = (listOf(language, language.group.defaultLanguage) + language.group.languages.filterNot {
        it != language && it != language.group.defaultLanguage
    }).mapNotNull { translationClasses[it] }.toSet() as Set<Translation<S>>

    translations.forEach { translation ->
        translation.apply {
            translation.SetTranslationsScope(this@applyLanguage).setTranslations()
        }
    }
}