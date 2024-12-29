# Auron
### Making android project development easier

**Auron allows you to:**
- have a main function
- have some parts of the android api simplified
- have an easy api for permissions

1. You need those third party plugins in your project:
   - `com.android.application` or `com.android.library` version `8.5.0`
   - kotlin multiplatform
2. You need to use the gradle taks on the following modules to publish the following modules to maven local
  :Application-Sdk :Sdk :Permissions :Plugin
3. Add maven local to your project's repos (both for dependencies as well as plugins)
4. Now add the auron plugin by using `id("pl.instah.Auron-Gradle") version "1.0.1A"`
5. Download the android sdk version `35`
6. Create a local.properties file and setup the android sdk dir
7. You should be now able to easily configure your project with auron

\*simplification of the process is planned

TODO:
- [ ] setting the app icon
- [ ] minification support
- [ ] whole project as common code
- [ ] automatic svg -> xml conversion
- [ ] stop using android.application and android.library plugins
- [X] permission support
- [X] common APIs
- [ ] common native API composables (like camera view)
- [ ] support for translations that doesn't use XML
- [ ] apple targets support