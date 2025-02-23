package io.instah.auron.sdk.language

enum class LanguageGroup(
    vararg val languages: Language,
    val defaultLanguage: Language = languages.first()
) {
    EN(Language.en_US, Language.en_GB),
    PL(Language.pl_PL)
}