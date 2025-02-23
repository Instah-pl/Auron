package io.instah.auron.sdk.language

fun <S : TranslationScheme> S.addTranslationClass(
    translationClass: Translation<S>
) {
    this.translationClasses[translationClass.language] = translationClass
}