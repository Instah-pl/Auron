package manifest

fun ManifestConfig.ManifestApplicationConfig?.generateManifestSection(): String {
    if (this == null) return ""

    return """
        <application
                android:allowBackup="true"
                android:label="${this.name}"
                android:supportsRtl="true"
                android:theme="@android:style/Theme.Material.Light.NoActionBar">
            <activity
                    android:exported="true"
                    android:configChanges="orientation|screenSize|screenLayout|keyboardHidden|mnc|colorMode|density|fontScale|fontWeightAdjustment|keyboard|layoutDirection|locale|mcc|navigation|smallestScreenSize|touchscreen|uiMode"
                    android:name="pl.instah.auron.MainActivity">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN" />

                    <category android:name="android.intent.category.LAUNCHER" />
                </intent-filter>
            </activity>
        </application>
    """
}