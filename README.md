Overview

The master branch of this repository contains application on WeatherForecast.

Prerequisites

The samples are building with compileSdkVersion 23 which requires JDK 7 or higher
Android Studio

Getting Started

These samples use the Gradle build system.

First download the samples by cloning this repository or downloading an archived snapshot. (See the options at the top of the page.)

In Android Studio, use the "Import non-Android Studio project" or "Import Project" option. Next select one of the sample directories that you downloaded from this repository. If prompted for a gradle configuration accept the default settings.

Run a sample

Once you have set up your developer environment you can run any sample from within Android Studio by selecting the app module from the Edit Configurations drop down and clicking the Run button from the toolbar.

Build/Run sample from Gradle

You can execute all the build tasks using the Gradle Wrapper command line tool. It's available as a batch file for Windows (gradlew.bat) and a shell script for Linux/Mac (gradlew.sh) and it is accessible from the root of the project.

Build a debug APK
$ ./gradlew assembleDebug
Run the app
Device

adb -d install path/to/sample.apk

Issues

Find a bug or want to request a new feature enhancement? Please let us know by submitting an issue.