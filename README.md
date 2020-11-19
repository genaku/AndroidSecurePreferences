# AndroidSecurePreferences
[![](https://jitpack.io/v/genaku/AndroidSecurePreferences.svg)](https://jitpack.io/#genaku/AndroidSecurePreferences)

Facade on Android Shared Preferences with data encryption. 
Data encryption based on [Themis cryptographic framework](https://github.com/cossacklabs/themis).

# Gradle setup
Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency to your app-level build.gradle:
```gradle
dependencies {
	        implementation 'com.github.genaku:AndroidSecurePreferences:1.0.3'
	}
```

# Usage
Very simple. You can see it in demo example.
