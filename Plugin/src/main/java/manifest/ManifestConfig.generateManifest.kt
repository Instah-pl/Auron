package manifest

fun ManifestConfig.generateManifest() = """
    <manifest xmlns:android="http://schemas.android.com/apk/res/android">

        <uses-permission android:name="android.permission.INTERNET" />
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
        
        ${this.applicationConfig.generateManifestSection()}
    </manifest>
""".trimIndent().split('\n').filter(String::isNotBlank)
    .joinToString(separator = "\n")