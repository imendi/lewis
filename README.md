# lewis
Rock your Android

## Purpose

This is an extension for Android Lint, adding new rules:
* RootPackageDetector: Every .java file must be inside a custom package, not inside the root package. ERROR
* MissingLauncherDetector: Every application must have at least one launcher activity. WARNING
* MoreThanOneLauncherDetector: The application must have only one launcher activity. WARNING

## Getting started

1. Clone the repository
```bash
git clone https://github.com/inaka/lewis.git
```

2. Enter to the project
```bash
cd lewis/
```

3. Build it
```bash
./gradlew build
```

4. Copy the .jar file to the lint folder
```bash
cp ./build/libs/lewis.jar ~/.android/lint/
```

5. Verify whether the issues are registered with lint
```bash
lint --show RootPackageDetector
```

6. Run lint
```bash
./gradlew lint
```
>   Note: If you can't run lint directly, you may want to include android tools PATH in your ~/.bash_profile. (i.e. PATH=$PATH:~/Library/Android/sdk/tools)
>
>    Then run source ~/.bash_profile.

#### Disable an issue
 Add on your build.gradle file
```groovy
android {
    lintOptions {
        disable 'RootPackageDetector','MoreThanOneLauncherDetector'
        ...
    }
}
```