# Auron
### Making android project development easier

**Auron allows you to:**
- have a main function
- have some parts of the android api simplified
- have an easy api for permissions

1. You need to add those plugins in your `plugins { ... }` block:
    - `id("com.android.application") version "8.7.3"` or `id("com.android.library") version "8.7.3"`
   \* based on your project type
    - `kotlin("multiplatform")`
2. Now add the auron plugin by using `id("pl.instah.Auron-Gradle") version "1.0.5A"`
3. Download the android sdk version `35`

You should be now able to easily configure your project with auron!

\*simplification of the process is planned

TODO:
- automatically download the android SDK [planned for 1.0.6 A]
- automatically install android plugins
- support projects that use newer kotlin versions
- automatic svg -> xml conversion
- setting the app icon
- minification support
- whole project as common code
- stop using android.application and android.library plugins
- common native API composables (like camera view)
- support for translations that doesn't use XML
- apple targets support