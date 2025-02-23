package com.example

import io.instah.auron.sdk.language.Language
import io.instah.auron.sdk.language.Translation
import io.instah.auron.sdk.language.TranslationManager
import io.instah.auron.sdk.language.TranslationScheme
import io.instah.auron.sdk.language.addTranslationClass

class ExampleTranslationScheme : TranslationScheme(Language.en_US) {
    override fun setTranslationClasses() {
        addTranslationClass(ExampleTranslationPolishLanguage)
    }

    val quit by field("Quit")

    companion object {
        val exampleTranslationSchemeHolder by TranslationManager.schemeHolder { ExampleTranslationScheme() }

        object ExampleTranslationPolishLanguage : Translation<ExampleTranslationScheme>(Language.pl_PL) {
            override fun SetTranslationsScope.setTranslations() {
                scheme::quit.setTranslation("Wyjdź")
            }
        }
    }
}