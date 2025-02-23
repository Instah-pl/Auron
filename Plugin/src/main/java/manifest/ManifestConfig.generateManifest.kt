package manifest

import korlibs.io.file.VfsFile
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun ManifestConfig.generateManifest(
    projectDir: VfsFile
) = """
    <manifest xmlns:android="http://schemas.android.com/apk/res/android">

        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
        <uses-permission android:name="android.permission.VIBRATE" />
        ${this.permissions.joinToString(
                separator = "\n"
            ) { permissionName ->
                "   <uses-permission android:name=\"${permissionName}\" />"
            }
        }
        
        ${this.usedFeatures.joinToString(
            separator = "\n"
            ) { featureName -> 
                "   <uses-feature android:name=\"${featureName}\" android:required=\"false\"/>"
            }
        }
        
        ${this.applicationConfig.generateManifestSection(projectDir)}
    </manifest>
""".trimIndent().split('\n').filter(String::isNotBlank)
    .joinToString(separator = "\n")